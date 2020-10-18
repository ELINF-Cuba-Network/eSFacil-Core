package cu.vlired.esFacilCore.messageListener;

import com.github.sonus21.rqueue.annotation.RqueueListener;
import cu.vlired.esFacilCore.constants.Condition;
import cu.vlired.esFacilCore.exception.SubmitFailException;
import cu.vlired.esFacilCore.model.SubmitTask;
import cu.vlired.esFacilCore.repository.DocumentRepository;
import cu.vlired.esFacilCore.repository.SubmitTaskRepository;
import cu.vlired.esFacilCore.strategy.submit.SubmitContext;
import cu.vlired.esFacilCore.strategy.submit.SubmitStrategyFactory;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@Log4j2
public class SubmitListener {

    @Autowired
    private SubmitTaskRepository submitTaskRepository;

    @Autowired
    private DocumentRepository documentRepository;

    @Autowired
    private SubmitStrategyFactory factory;

    @RqueueListener(value = "${app.queue.submit.name}")
    public void submit(UUID submitTaskId) {

        log.info("Processing submit task " + submitTaskId);

        var exist = submitTaskRepository.findById(submitTaskId);

        if (exist.isPresent()) {
            var task = exist.get();

            var config = task.getConfig();

            log.info("Config info " + config);

            var strategy = factory.getStrategy(config);
            var ctx = new SubmitContext(strategy);

            try {

                ctx.execute(task);
                task.setStatus(SubmitTask.STATUS_OK);

                updateStatus(task, Condition.SUBMITTED);

            } catch (SubmitFailException ex) {

                log.error("Submitting failed");
                log.error(ExceptionUtils.getStackTrace(ex));

                task.setErrorMessage(ex.getMessage());
                task.setStatus(SubmitTask.STATUS_KO);

                updateStatus(task, Condition.FAIL);
            }
        }

    }

    private void updateStatus(SubmitTask task, String condition) {
        var doc = task.getDocument();
        doc.setCondition(condition);

        submitTaskRepository.save(task);
        documentRepository.save(doc);
    }

}

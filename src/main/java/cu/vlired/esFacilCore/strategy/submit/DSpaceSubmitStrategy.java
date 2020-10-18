package cu.vlired.esFacilCore.strategy.submit;

import com.github.sonus21.rqueue.core.RqueueMessageEnqueuer;
import cu.vlired.esFacilCore.dto.queue.PostBitstreamDTO;
import cu.vlired.esFacilCore.exception.SubmitFailException;
import cu.vlired.esFacilCore.model.SubmitTask;
import cu.vlired.esFacilCore.services.DspaceService;
import cu.vlired.esFacilCore.util.DSpaceURLHelper;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;

@Component
@Log4j2
public class DSpaceSubmitStrategy implements SubmitStrategy {

    @Autowired
    private DspaceService dspaceService;

    @Autowired
    private RqueueMessageEnqueuer enqueuer;

    @Autowired
    private Environment environment;

    @Override
    public void submit(SubmitTask submitTask) {
        try {
            var config = submitTask.getConfig();

            var username = config.getData().getUsername();
            var password = config.getData().getPassword();

            var urlHelper = new DSpaceURLHelper(config.getData());
            var url = urlHelper.buildLoginURL();

            var cookie = dspaceService.login(username, password, url);
            var itemDTO = dspaceService.postItem(cookie, submitTask);

            var queue = environment.getProperty("app.queue.postBitstream.name");

            var doc = submitTask.getDocument();

            doc.getBitstreams().forEach(b -> {
                var dto = new PostBitstreamDTO(
                    b.getId(),
                    submitTask.getId(),
                    itemDTO.getUuid().toString(),
                    cookie
                );

                enqueuer.enqueue(queue, dto);
            });
        } catch (Exception ex) {

            log.error("Submitting failed");
            log.error(ExceptionUtils.getStackTrace(ex));

            String message = "";

            if (ex instanceof HttpStatusCodeException) {
                message = ((HttpStatusCodeException) ex).getResponseBodyAsString();
            } else {
                message = ex.getMessage();
            }

            throw new SubmitFailException(message);
        }

    }

}

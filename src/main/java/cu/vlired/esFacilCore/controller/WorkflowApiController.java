package cu.vlired.esFacilCore.controller;

import com.github.sonus21.rqueue.core.RqueueMessageEnqueuer;
import cu.vlired.esFacilCore.api.WorkflowApi;
import cu.vlired.esFacilCore.components.I18n;
import cu.vlired.esFacilCore.components.ResponsesHelper;
import cu.vlired.esFacilCore.dto.RejectDTO;
import cu.vlired.esFacilCore.exception.ResourceNotFoundException;
import cu.vlired.esFacilCore.model.SubmitConfig;
import cu.vlired.esFacilCore.model.SubmitTask;
import cu.vlired.esFacilCore.model.User;
import cu.vlired.esFacilCore.repository.SubmitConfigRepository;
import cu.vlired.esFacilCore.repository.SubmitTaskRepository;
import cu.vlired.esFacilCore.services.WorkflowService;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class WorkflowApiController implements WorkflowApi {

    @Value("${app.queue.submit.name}")
    String queueName;

    private final WorkflowService workflowService;
    private final ResponsesHelper responsesHelper;
    private final SubmitConfigRepository submitConfigRepository;
    private final SubmitTaskRepository submitTaskRepository;
    private final RqueueMessageEnqueuer enqueuer;
    private final I18n i18n;

    WorkflowApiController(
        WorkflowService workflowService,
        ResponsesHelper responsesHelper,
        SubmitConfigRepository submitConfigRepository,
        SubmitTaskRepository submitTaskRepository,
        RqueueMessageEnqueuer enqueuer,
        I18n i18n
    ) {
        this.workflowService = workflowService;
        this.responsesHelper = responsesHelper;
        this.submitConfigRepository = submitConfigRepository;
        this.submitTaskRepository = submitTaskRepository;
        this.enqueuer = enqueuer;
        this.i18n = i18n;
    }

    @Override
    public ResponseEntity<?> processDocument(UUID id) {
        workflowService.processDocument(id);
        return responsesHelper.okNoData();
    }

    @Override
    public ResponseEntity<?> reviewDocument(UUID id, User currentUser) {
        workflowService.reviewDocument(id, currentUser);
        return responsesHelper.okNoData();
    }

    @Override
    public ResponseEntity<?> rejectDocument(UUID id, RejectDTO data, User currentUser) {
        workflowService.rejectDocument(id, data, currentUser);
        return responsesHelper.okNoData();
    }

    @Override
    public ResponseEntity<?> submitDocument(UUID id, UUID configId, User currentUser) {
        var document = workflowService.submitDocument(id, currentUser);

        var config = submitConfigRepository.findById(configId)
            .orElseThrow(
                () -> new ResourceNotFoundException(
                    i18n.t("app.document.id.not.found", ArrayUtils.toArray(configId)))
            )
        ;

        var submitTask = new SubmitTask();
        submitTask.setDocument(document);
        submitTask.setConfig(config);
        submitTask.setStatus(SubmitTask.STATUS_PENDING);

        submitTaskRepository.save(submitTask);
        enqueuer.enqueue(queueName, submitTask.getId());

        return responsesHelper.okNoData();
    }
}

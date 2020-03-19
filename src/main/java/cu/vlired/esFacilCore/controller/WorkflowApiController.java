package cu.vlired.esFacilCore.controller;

import cu.vlired.esFacilCore.api.WorkflowApi;
import cu.vlired.esFacilCore.components.ResponsesHelper;
import cu.vlired.esFacilCore.services.WorkflowService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class WorkflowApiController implements WorkflowApi {

    private final WorkflowService workflowService;
    private final ResponsesHelper responsesHelper;

    WorkflowApiController(
        WorkflowService workflowService,
        ResponsesHelper responsesHelper
    ) {
        this.workflowService = workflowService;
        this.responsesHelper = responsesHelper;
    }

    @Override
    public ResponseEntity<?> processDocument(UUID id) {
        workflowService.processDocument(id);
        return responsesHelper.okNoData();
    }
}

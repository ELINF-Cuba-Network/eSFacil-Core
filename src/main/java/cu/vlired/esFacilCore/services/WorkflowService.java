package cu.vlired.esFacilCore.services;

import cu.vlired.esFacilCore.components.I18n;
import cu.vlired.esFacilCore.constants.Condition;
import cu.vlired.esFacilCore.exception.InvalidWorkflowException;
import cu.vlired.esFacilCore.exception.ResourceNotFoundException;
import cu.vlired.esFacilCore.model.Document;
import cu.vlired.esFacilCore.repository.DocumentRepository;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class WorkflowService {

    private final I18n i18n;
    private final DocumentRepository documentRepository;

    WorkflowService(
        I18n i18n,
        DocumentRepository documentRepository
    ) {
        this.i18n = i18n;
        this.documentRepository = documentRepository;
    }

    public void processDocument(UUID documentId) {
        Document document = documentRepository.findById(documentId)
            .orElseThrow(
                () -> new ResourceNotFoundException(
                    i18n.t("app.document.id.not.found", ArrayUtils.toArray(documentId)))
            );

        checkWorkflowRules(document, Condition.IN_PROCESS);

        document.setCondition(Condition.IN_PROCESS);
        documentRepository.save(document);
    }

    public void checkWorkflowRules(Document document, String to) throws InvalidWorkflowException {
        switch (document.getCondition()) {

            case Condition.WORKSPACE:
                checkWorkspaceChange(document, to);
                break;

            case Condition.IN_PROCESS:
                checkInProcessChange(document, to);
                break;

            case Condition.IN_REVIEWER:
                checkInReviewerChange(document, to);
                break;

            case Condition.REJECTED:
                checkInRejectedChange(document, to);
                break;

            case Condition.FOR_SUBMIT:
                checkForSubmitChange(document, to);
                break;

            case Condition.FAIL:
                checkFailChange(document, to);
                break;
        }
    }

    private void checkWorkspaceChange(Document document, String to) {
        if (!to.equals(Condition.IN_PROCESS) && !to.equals(Condition.WORKSPACE)) {
            throwException(document, to);
        }
    }

    private void checkInProcessChange(Document document, String to) {
        if (!to.equals(Condition.IN_REVIEWER) && !to.equals(Condition.IN_PROCESS)) {
            throwException(document, to);
        }
    }

    private void checkInRejectedChange(Document document, String to) {
        if (!to.equals(Condition.IN_REVIEWER) && !to.equals(Condition.REJECTED)) {
            throwException(document, to);
        }
    }

    private void checkInReviewerChange(Document document, String to) {
        if (!to.equals(Condition.FOR_SUBMIT) && !to.equals(Condition.REJECTED) && !to.equals(Condition.IN_REVIEWER)) {
            throwException(document, to);
        }
    }

    private void checkForSubmitChange(Document document, String to) {
        if (!to.equals(Condition.SUBMITTED) && !to.equals(Condition.FAIL) && !to.equals(Condition.FOR_SUBMIT)) {
            throwException(document, to);
        }
    }

    private void checkFailChange(Document document, String to) {
        if (!to.equals(Condition.SUBMITTED) && !to.equals(Condition.FAIL)) {
            throwException(document, to);
        }
    }

    private void throwException(Document document, String to) {
        throw new InvalidWorkflowException(
            i18n.t(
                "app.workflow.invalid",
                ArrayUtils.toArray(document.getCondition(), to)
            )
        );
    }

}

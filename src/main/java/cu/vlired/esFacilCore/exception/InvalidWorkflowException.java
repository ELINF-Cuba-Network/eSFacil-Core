package cu.vlired.esFacilCore.exception;

import cu.vlired.esFacilCore.constants.Codes;

public class InvalidWorkflowException extends BaseException {

    public InvalidWorkflowException(String message) {
        super(message, Codes.INVALID_WORKFLOW);
    }

}

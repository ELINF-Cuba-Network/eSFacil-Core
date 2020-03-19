package cu.vlired.esFacilCore.exception;

import cu.vlired.esFacilCore.constants.Codes;

public class ValidationException extends BaseException {

    public ValidationException(String message) {
        super(message, Codes.VALIDATION_ERROR);
    }
}

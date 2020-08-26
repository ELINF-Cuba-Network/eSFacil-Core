package cu.vlired.esFacilCore.exception;

import cu.vlired.esFacilCore.constants.Codes;

public class ForbiddenException extends BaseException {

    public ForbiddenException(String message) {
        super(message, Codes.FORBIDDEN_ERROR);
    }
}

package cu.vlired.esFacilCore.exception;

import cu.vlired.esFacilCore.constants.Codes;

public class ResourceAlreadyTakenException extends BaseException {

    public ResourceAlreadyTakenException(String message) {
        super(message, Codes.RESOURCE_ALREADY_TAKEN);
    }

}

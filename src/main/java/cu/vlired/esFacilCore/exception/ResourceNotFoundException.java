package cu.vlired.esFacilCore.exception;

import cu.vlired.esFacilCore.constants.Codes;

public class ResourceNotFoundException extends BaseException {

    public ResourceNotFoundException(String message) {
        super(message, Codes.RESOURCE_NOT_FOUND);
    }
}


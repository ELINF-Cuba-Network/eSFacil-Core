package cu.vlired.esFacilCore.exception;

import cu.vlired.esFacilCore.constants.Codes;

public class StorageException extends BaseException {

    public StorageException(String message) {
        super(message, Codes.STORAGE_ERROR);
    }

}

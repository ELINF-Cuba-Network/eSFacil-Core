package cu.vlired.esFacilCore.exception;

import cu.vlired.esFacilCore.constants.Codes;

public class FieldNotFilterableException extends BaseException {

    public FieldNotFilterableException(String message) {
        super(message, Codes.FIELD_NOT_FILTERABLE);
    }
}

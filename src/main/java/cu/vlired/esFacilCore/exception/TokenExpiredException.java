package cu.vlired.esFacilCore.exception;

import cu.vlired.esFacilCore.constants.Codes;

public class TokenExpiredException extends BaseException {

    public TokenExpiredException(String message) {
        super(message, Codes.TOKEN_EXPIRED);
    }

}

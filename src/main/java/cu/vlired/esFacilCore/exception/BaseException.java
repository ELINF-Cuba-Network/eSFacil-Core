package cu.vlired.esFacilCore.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BaseException extends RuntimeException {

    private String code;

    public BaseException(String message, String code) {
        super(message);
        this.code = code;
    }

}

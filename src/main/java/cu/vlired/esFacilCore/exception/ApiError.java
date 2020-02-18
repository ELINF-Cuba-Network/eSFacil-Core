package cu.vlired.esFacilCore.exception;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Setter;

import java.text.SimpleDateFormat;
import java.util.Date;

@Setter
public class ApiError {

    @JsonProperty
    private String code;

    @JsonProperty
    private String message;

    private Date timestamp;

    private SimpleDateFormat simpleDateFormat;

    public ApiError() {
        simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
    }

    @JsonGetter("timestamp")
    public String getTimestamp() {
        return simpleDateFormat.format(timestamp);
    }
}

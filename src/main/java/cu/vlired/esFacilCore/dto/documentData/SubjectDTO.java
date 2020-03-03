package cu.vlired.esFacilCore.dto.documentData;

import com.fasterxml.jackson.annotation.JsonProperty;
import cu.vlired.esFacilCore.dto.BaseDTO;
import lombok.*;

@Setter
@Getter
@ToString
public class SubjectDTO extends BaseDTO {
    private String value;
    private String key;

    @JsonProperty("key-asserted-by")
    private String keyAssertedBy;
}

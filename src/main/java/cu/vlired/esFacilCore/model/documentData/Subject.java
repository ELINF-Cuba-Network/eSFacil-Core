package cu.vlired.esFacilCore.model.documentData;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
public class Subject {
    private String value;
    private String key;

    @JsonProperty("key-asserted-by")
    private String keyAssertedBy;
}

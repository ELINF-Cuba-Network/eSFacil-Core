package cu.vlired.esFacilCore.model.documentData;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
public class Issued {
    @JsonProperty("date-parts")
    private List<String> dateParts;
}

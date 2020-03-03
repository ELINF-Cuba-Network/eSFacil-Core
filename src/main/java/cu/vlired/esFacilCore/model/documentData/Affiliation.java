package cu.vlired.esFacilCore.model.documentData;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Affiliation {
    private String name;
    private String uri;
}

package cu.vlired.esFacilCore.model.documentData;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Author {
    private String family;
    private String given;
    private String uri;
    private List<Affiliation> affiliation;
}

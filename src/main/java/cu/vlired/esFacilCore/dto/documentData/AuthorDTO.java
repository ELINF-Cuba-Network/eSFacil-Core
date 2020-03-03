package cu.vlired.esFacilCore.dto.documentData;

import cu.vlired.esFacilCore.dto.BaseDTO;
import cu.vlired.esFacilCore.model.documentData.Affiliation;
import lombok.*;

import java.util.List;

@Setter @Getter @ToString
public class AuthorDTO extends BaseDTO {
    private String family;
    private String given;
    private String uri;
    private List<Affiliation> affiliation;
}

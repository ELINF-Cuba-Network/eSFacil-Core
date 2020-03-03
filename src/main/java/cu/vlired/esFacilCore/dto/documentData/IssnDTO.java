package cu.vlired.esFacilCore.dto.documentData;

import cu.vlired.esFacilCore.dto.BaseDTO;
import lombok.*;

@Setter
@Getter
@ToString
public class IssnDTO extends BaseDTO {
    private String value;
    private String type;
}

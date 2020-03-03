package cu.vlired.esFacilCore.dto.documentData;

import com.fasterxml.jackson.annotation.JsonProperty;
import cu.vlired.esFacilCore.dto.BaseDTO;
import lombok.*;

import java.util.List;

@Setter
@Getter
@ToString
public class IssuedDTO extends BaseDTO {
    @JsonProperty("date-parts")
    private List<String> dateParts;
}

package cu.vlired.esFacilCore.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class BitstreamDTO extends BaseDTO {

    private String name;
    private String extension;
    private String code;
    private String description;

}

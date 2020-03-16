package cu.vlired.esFacilCore.dto;

import cu.vlired.esFacilCore.dto.documentData.DocumentDataDTO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Setter @Getter @ToString
public class DocumentDTO extends BaseDTO {

    private DocumentDataDTO data;
    private List<BitstreamDTO> bitstreams;
    private String condition;
    private UserDTO person;

}

package cu.vlired.esFacilCore.dto;

import cu.vlired.esFacilCore.dto.documentData.DocumentDataDTO;
import cu.vlired.esFacilCore.model.Bitstream;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Setter @Getter @ToString
public class DocumentDTO extends BaseDTO {

    private DocumentDataDTO data;
    private List<Bitstream> bitstreams;
    private String condition;
    private UserDTO person;

}

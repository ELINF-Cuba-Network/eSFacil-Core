package cu.vlired.esFacilCore.dto;

import cu.vlired.esFacilCore.dto.configFormData.SubmitConfigDataDTO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString
public class PatchSubmitConfigDTO extends BaseDTO {

    private String type;
    private boolean active;
    private SubmitConfigDataDTO data;

    public PatchSubmitConfigDTO() {
    }
}

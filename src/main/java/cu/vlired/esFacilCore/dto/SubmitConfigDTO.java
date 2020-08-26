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
public class SubmitConfigDTO extends BaseDTO {

    @NotBlank(message = "{app.submit-config.type.required}")
    private String type;

    @NotNull(message = "{app.submit-config.active.required}")
    private boolean active;

    @NotNull(message = "{app.submit-config.active.required}")
    private SubmitConfigDataDTO data;

    public SubmitConfigDTO() {
    }
}

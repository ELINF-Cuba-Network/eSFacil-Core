package cu.vlired.esFacilCore.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Size;

@Setter
@Getter
@ToString
public class RejectDTO extends BaseDTO {

    @Size(
        message = "{app.validation.message.max.value}",
        max = 500
    )
    private String message;

}

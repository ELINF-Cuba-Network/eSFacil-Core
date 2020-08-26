package cu.vlired.esFacilCore.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Setter @Getter @ToString
public class PatchUserDTO extends BaseDTO {
    @Size(
            message = "{app.validation.username.max.value}",
            max = 20
    )
    private String username;

    @Size(
            message = "{app.validation.email.between.value}",
            min = 5,
            max = 12
    )
    @JsonProperty(value="password", access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @Size(
            message = "{app.validation.firstname.max.value}",
            max = 20
    )
    private String firstname;

    @Size(
            message = "{app.validation.lastname.max.value}",
            max = 20
    )
    private String lastname;

    @Email(message = "{app.validation.email.invalid}")
    private String email;

    private List<String> roles = new ArrayList<>();

    private boolean active;

    public PatchUserDTO() {
    }
}
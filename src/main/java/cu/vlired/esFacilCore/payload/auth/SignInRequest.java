package cu.vlired.esFacilCore.payload.auth;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Setter @Getter
@ToString
public class SignInRequest {

    @NotBlank(message = "{app.validation.username.required}")
    @Email(message = "{app.validation.email.invalid}")
    private String username;

    @NotBlank
    @Size(
            message = "{app.validation.password.between.value}",
            min = 5,
            max = 12
    )
    private String password;

}

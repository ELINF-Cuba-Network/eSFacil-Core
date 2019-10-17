package cu.vlired.submod.payload;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.UUID;

@Setter @Getter
@ToString
public class UserExistRequest {

    private String  username;
    private String  email;

    private UUID id = null;
}

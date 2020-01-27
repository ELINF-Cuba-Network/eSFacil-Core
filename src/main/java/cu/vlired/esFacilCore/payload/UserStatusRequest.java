package cu.vlired.esFacilCore.payload;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
public class UserStatusRequest {
    private String token;
}

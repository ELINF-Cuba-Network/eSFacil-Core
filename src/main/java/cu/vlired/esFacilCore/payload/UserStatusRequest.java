package cu.vlired.esFacilCore.payload;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter @Getter @ToString
public class UserStatusRequest {
    private String token;
}

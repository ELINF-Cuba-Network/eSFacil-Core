package cu.vlired.esFacilCore.dto.dspace;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.UUID;

@Setter @Getter @ToString
public class ItemDTO {
    private UUID uuid;
    private String name;
    private String handle;
    private String type;
    private String link;
}

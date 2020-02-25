package cu.vlired.esFacilCore.model.dto;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Id;
import java.util.UUID;

@Setter @Getter
public class BaseDTO {
    @Id
    private UUID id;
}

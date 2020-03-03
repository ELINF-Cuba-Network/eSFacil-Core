package cu.vlired.esFacilCore.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Id;
import java.util.UUID;

@Setter @Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BaseDTO {
    @Id
    private UUID id;
}

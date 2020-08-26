package cu.vlired.esFacilCore.dto.configFormData;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter @Getter @ToString
public class SubmitConfigDataDTO {
    protected String ip;
    protected String port;
    protected String endpoint;
    private String collectionId;
}

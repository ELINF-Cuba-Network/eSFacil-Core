package cu.vlired.esFacilCore.model.configFormData;

import lombok.*;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class SubmitConfigData {
    protected String ip;
    protected String port;
    protected String endpoint;
    private String collectionId;
}

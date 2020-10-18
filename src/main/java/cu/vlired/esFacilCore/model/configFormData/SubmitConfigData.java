package cu.vlired.esFacilCore.model.configFormData;

import lombok.*;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class SubmitConfigData {
    protected String username;
    protected String password;
    protected String ip;
    protected String port;
    protected String schema;
    protected String endpoint;
    private String collectionId;
}

package cu.vlired.esFacilCore.dto.queue;

import lombok.*;

import java.util.UUID;

@Setter @Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PostBitstreamDTO {

    private UUID bitstreamId;
    private UUID taskId;
    private String itemID;
    private String cookie;

}

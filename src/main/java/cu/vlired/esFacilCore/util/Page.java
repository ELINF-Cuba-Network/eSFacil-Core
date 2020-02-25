package cu.vlired.esFacilCore.util;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.Map;

@Setter
@Getter
@Builder
@AllArgsConstructor
@ToString
public class Page {

    private int limit;
    private int page;
    private String sort;
    private String order;
    private String q;
    private long total;

    public Page() {
    }
}

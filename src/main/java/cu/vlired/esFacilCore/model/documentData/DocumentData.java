package cu.vlired.esFacilCore.model.documentData;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
public class DocumentData {
    private List<Author> author;
    private List<String> title;
    private List<String> language;
    private List<Issued> issued;
    private List<String> type;
    private List<String> subject;

    @JsonProperty("subject-id")
    private List<Subject> subjectId;

    @JsonProperty("abstract")
    private List<String> _abstract;

    private List<String> issn;

    @JsonProperty("issn-type")
    private List<Issn> issnType;

    private List<String> publisher;
    private List<String> doi;
    private List<String> page;
    private List<String> volume;

    @JsonProperty("container-title")
    private List<String> containerTitle;
}


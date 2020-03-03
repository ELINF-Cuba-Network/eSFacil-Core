package cu.vlired.esFacilCore.dto.documentData;

import com.fasterxml.jackson.annotation.JsonProperty;
import cu.vlired.esFacilCore.dto.BaseDTO;
import lombok.*;

import java.util.List;

@Setter
@Getter
@ToString
public class DocumentDataDTO extends BaseDTO {
    private List<AuthorDTO> author;
    private List<String> title;
    private List<String> language;
    private List<IssuedDTO> issued;
    private List<String> type;
    private List<String> subject;

    @JsonProperty("subject-id")
    private List<SubjectDTO> subjectId;

    @JsonProperty("abstract")
    private List<String> _abstract;

    private List<String> issn;

    @JsonProperty("issn-type")
    private List<IssnDTO> issnType;

    private List<String> publisher;
    private List<String> doi;
    private List<String> page;
    private List<String> volume;

    @JsonProperty("container-title")
    private List<String> containerTitle;
}


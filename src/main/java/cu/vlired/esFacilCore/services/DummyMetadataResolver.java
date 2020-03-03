package cu.vlired.esFacilCore.services;

import cu.vlired.esFacilCore.model.documentData.Author;
import cu.vlired.esFacilCore.model.documentData.DocumentData;
import cu.vlired.esFacilCore.model.documentData.Issn;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@Service
@Primary
public class DummyMetadataResolver implements MetadataResolver {

    @Override
    public DocumentData getMetadataFromFile(MultipartFile file) {

        return DocumentData
            .builder()
            .author(
                Collections.singletonList(
                    Author
                        .builder()
                        .family("de Tobias")
                        .given("El Bueno")
                        .build()
                )
            )
            .title(
                Collections.singletonList("Un Chino muy chino")
            )
            .publisher(
                Collections.singletonList("Loulogio")
            )
            .type(
                Collections.singletonList("Journal")
            )
            .issnType(
                Collections.singletonList(
                    Issn
                        .builder()
                        .type("print")
                        .value("1073-449X")
                        .build()
                )
            )
            .build();
    }

    @Override
    public Map<String, List<String>> processResponse(Object data) {
        return null;
    }
}

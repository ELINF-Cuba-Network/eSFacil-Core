package cu.vlired.esFacilCore.services;

import cu.vlired.esFacilCore.constants.Condition;
import cu.vlired.esFacilCore.model.Bitstream;
import cu.vlired.esFacilCore.model.Document;
import cu.vlired.esFacilCore.model.documentData.DocumentData;
import cu.vlired.esFacilCore.model.User;
import cu.vlired.esFacilCore.dto.DocumentDTO;
import cu.vlired.esFacilCore.repository.DocumentRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Service
public class DocumentService {

    @Value("${dir.config}")
    private String dir_config;

    private final BitstreamService bitstreamService;
    private final DocumentRepository documentRepository;
    private final DTOUtilService dtoUtilService;
    private final MetadataResolver metadataResolver;

    public DocumentService(
        BitstreamService bitstreamService,
        DocumentRepository documentRepository,
        DTOUtilService dtoUtilService,
        MetadataResolver metadataResolver
    ) {
        this.bitstreamService = bitstreamService;
        this.documentRepository = documentRepository;
        this.dtoUtilService = dtoUtilService;
        this.metadataResolver = metadataResolver;
    }

    public DocumentDTO createFromFile(MultipartFile file, User user) throws IOException {
        // Create a bitstream using the file
        Bitstream bitstream = bitstreamService
            .createBitstreamFromFile(file);

        // Getting the bitstream metadata from Darkaiv
        DocumentData documentData = metadataResolver
            .getMetadataFromFile(file);

        // I need to Map the Darkaiv response to a CSL format
        // mapData has the darkaiv key to => CSL key
        // TODO: When service (Darkaiv or GROBID was ready move this to metadata resolver service)
        byte[] mapData = Files.readAllBytes(
            Paths.get(dir_config, "DarkaivApiToCSLMap.json")
        );

        // Create the document
        Document doc = new Document();
        doc.setData(documentData);
        doc.addBitstream(bitstream);
        doc.setPerson(user);

        // By default when submit a new file is in process
        doc.setCondition(Condition.IN_PROCESS);
        Document document = documentRepository.save(doc);

        return dtoUtilService.convertToDTO(document, DocumentDTO.class);
    }

}

package cu.vlired.esFacilCore.api;

import cu.vlired.esFacilCore.components.ResponsesHelper;
import cu.vlired.esFacilCore.constants.Condition;
import cu.vlired.esFacilCore.model.Bitstream;
import cu.vlired.esFacilCore.model.Document;
import cu.vlired.esFacilCore.model.DocumentData.Author;
import cu.vlired.esFacilCore.model.DocumentData.DocumentData;
import cu.vlired.esFacilCore.repository.DocumentRepository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

import cu.vlired.esFacilCore.services.BitstreamService;
import cu.vlired.esFacilCore.services.MetadataResolver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class DocumentApiController implements DocumentApi {

    @Value("${dir.config}")
    private String dir_config;

    private final DocumentRepository documentRepository;
    private final BitstreamService bitstreamService;
    private final MetadataResolver metadataResolver;
    private final ResponsesHelper responseHelper;

    public DocumentApiController(
        DocumentRepository documentRepository,
        BitstreamService bitstreamService,
        MetadataResolver metadataResolver,
        ResponsesHelper responseHelper
    ) {
        this.documentRepository = documentRepository;
        this.bitstreamService = bitstreamService;
        this.metadataResolver = metadataResolver;
        this.responseHelper = responseHelper;
    }

    @Override
    public ResponseEntity<Document> createDocument(@RequestBody Document document) {
        Document saveDocument = documentRepository.save(document);
        return responseHelper.buildResponse(saveDocument, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<Document>> getAllDocuments() {
        List<Document> listDocuments = documentRepository.findAll();
        return ResponseEntity.ok().body(listDocuments);
    }

    @Override
    public ResponseEntity<List<Document>> searchDocument(String term) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ResponseEntity<?> deleteDocument(long document_id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ResponseEntity<Document> CreateDocumentFromFile(MultipartFile file) throws IOException {
        // Create a bitstream using the file
        Bitstream bitstream = bitstreamService.createBitstreamFromFile(file);

        // Getting the bitstream metadata from Darkaiv
        DocumentData documentData = metadataResolver.getMetadataFromFile(file);

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

        // By default when submit a new file is in process
        doc.setCondition(Condition.IN_PROCESS);
        documentRepository.save(doc);

        return responseHelper.ok(doc);
    }

}

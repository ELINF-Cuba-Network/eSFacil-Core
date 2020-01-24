package cu.vlired.esFacilCore.api;

import cu.vlired.esFacilCore.components.ResponsesHelper;
import cu.vlired.esFacilCore.model.Bitstream;
import cu.vlired.esFacilCore.model.Document;
import cu.vlired.esFacilCore.repository.DocumentRepository;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DocumentApiController implements DocumentApi {

    private final DocumentRepository documentRepository;
    private final ResponsesHelper responseHelper;

    public DocumentApiController(
            DocumentRepository documentRepository,
            ResponsesHelper responseHelper
    ) {
        this.documentRepository = documentRepository;
        this.responseHelper = responseHelper;
    }

    @Override
    public ResponseEntity<Document> createDocument(@RequestBody Document document) {
        Document saveDocument = documentRepository.save(document);
        return responseHelper.buildResponse(saveDocument, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<Document>> getAllDocuments() {
       List<Document> listDocuments =  documentRepository.findAll();
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
    public ResponseEntity<Document> createDocumentfromFile(Bitstream bitstream) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}

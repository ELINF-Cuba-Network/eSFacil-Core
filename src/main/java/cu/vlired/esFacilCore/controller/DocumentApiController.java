package cu.vlired.esFacilCore.controller;

import cu.vlired.esFacilCore.api.DocumentApi;
import cu.vlired.esFacilCore.components.ResponsesHelper;
import cu.vlired.esFacilCore.model.Document;
import cu.vlired.esFacilCore.model.User;
import cu.vlired.esFacilCore.dto.DocumentDTO;
import cu.vlired.esFacilCore.repository.DocumentRepository;

import java.io.IOException;
import java.util.*;

import cu.vlired.esFacilCore.services.DocumentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class DocumentApiController implements DocumentApi {

    private final DocumentRepository documentRepository;
    private final DocumentService documentService;
    private final ResponsesHelper responseHelper;

    public DocumentApiController(
        DocumentRepository documentRepository,
        DocumentService documentService,
        ResponsesHelper responseHelper
    ) {
        this.documentRepository = documentRepository;
        this.documentService = documentService;
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
    public ResponseEntity<?> createDocumentFromFile(MultipartFile file, User user)
        throws IOException {
        DocumentDTO doc = documentService.createFromFile(file, user);
        return responseHelper.ok(doc);
    }

}

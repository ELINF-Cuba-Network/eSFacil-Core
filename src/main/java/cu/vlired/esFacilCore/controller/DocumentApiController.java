package cu.vlired.esFacilCore.controller;

import cu.vlired.esFacilCore.api.DocumentApi;
import cu.vlired.esFacilCore.components.ResponsesHelper;
import cu.vlired.esFacilCore.dto.DocumentDTO;
import cu.vlired.esFacilCore.dto.documentData.DocumentDataDTO;
import cu.vlired.esFacilCore.model.User;
import cu.vlired.esFacilCore.services.DocumentService;
import cu.vlired.esFacilCore.util.Page;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
@Log4j2
public class DocumentApiController implements DocumentApi {

    private final DocumentService documentService;
    private final ResponsesHelper responseHelper;

    public DocumentApiController(
        DocumentService documentService,
        ResponsesHelper responseHelper
    ) {
        this.documentService = documentService;
        this.responseHelper = responseHelper;
    }

    @Override
    public ResponseEntity<?> createDocument(DocumentDataDTO dataDTO, User user) {
        DocumentDTO saveDocument = documentService.createFromData(dataDTO, user);
        return responseHelper.buildResponse(saveDocument, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> listDocuments(Page page) {
        List<DocumentDTO> documentDTOS = documentService.list(page);
        return responseHelper.buildResponse(documentDTOS, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> getDocumentById(UUID id) {
        DocumentDTO documentDTO = documentService.getById(id);
        return responseHelper.buildResponse(documentDTO, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> deleteDocument(UUID id) {
        documentService.deleteById(id);
        return responseHelper.okNoData();
    }

    @Override
    public ResponseEntity<?> updateDocument(UUID id, @Valid DocumentDataDTO dataDTO) {
        DocumentDTO documentDTO = documentService.update(id, dataDTO);
        return responseHelper.buildResponse(documentDTO, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> patchDocument(UUID id, @Valid DocumentDataDTO dataDTO) {
        DocumentDTO documentDTO = documentService.patch(id, dataDTO);
        return responseHelper.buildResponse(documentDTO, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> createDocumentFromFile(MultipartFile file, User user)
        throws IOException {
        DocumentDTO doc = documentService.createFromFile(file, user);
        return responseHelper.ok(doc);
    }

    @Override
    public ResponseEntity<?> listMe(Page page, User currentUser) {
        log.info(String.format("User %s want his documents", currentUser.getEmail()));

        List<DocumentDTO> documentDTOS = documentService.listMe(page, currentUser);
        return responseHelper.buildResponse(documentDTOS, HttpStatus.OK);
    }

}

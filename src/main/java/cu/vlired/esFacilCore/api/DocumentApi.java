package cu.vlired.esFacilCore.api;

import cu.vlired.esFacilCore.dto.DocumentDTO;
import cu.vlired.esFacilCore.dto.documentData.DocumentDataDTO;
import cu.vlired.esFacilCore.model.Document;
import cu.vlired.esFacilCore.model.User;
import cu.vlired.esFacilCore.dto.UserDTO;
import cu.vlired.esFacilCore.model.documentData.DocumentData;
import cu.vlired.esFacilCore.security.CurrentUser;
import cu.vlired.esFacilCore.util.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

@Api(value = "Documents", tags = {"Document"})
@RequestMapping("/document")
public interface DocumentApi {

    @ApiOperation(
        value = "Create a document",
        response = DocumentDTO.class
    )
    @RequestMapping(method = RequestMethod.POST)
    ResponseEntity<?> createDocument(
        @ApiParam(required = true) @Valid @RequestBody DocumentDataDTO dataDTO,
        @CurrentUser User user
    );

    @ApiOperation(value = "List documents", response = List.class)
    @RequestMapping(method = RequestMethod.GET)
    ResponseEntity<?> listDocuments(
        Page page
    );

    @ApiOperation(value = "Get document by id", response = DocumentDTO.class)
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    ResponseEntity<?> getDocumentById(
        @ApiParam(required = true) @PathVariable UUID id
    );

    @ApiOperation(value = "Delete document by id")
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    ResponseEntity<?> deleteDocument(
        @ApiParam(required = true) @PathVariable UUID id
    );

    @ApiOperation(value = "Update document")
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    ResponseEntity<?> updateDocument(
        @PathVariable("id") UUID id,
        @Valid @RequestBody DocumentDataDTO dataDTO
    );

    @ApiOperation(value = "Patch document")
    @RequestMapping(value = "/{id}", method = RequestMethod.PATCH)
    ResponseEntity<?> patchDocument(
        @PathVariable("id") UUID id,
        @Valid @RequestBody DocumentDataDTO dataDTO
    );

    @ApiOperation(
            value = "Create a document",
            notes = "Create a document uploading a file",
            response = DocumentDTO.class
    )
    @RequestMapping(
            value = "/upload",
            headers = ("content-type=multipart/*"),
            method = RequestMethod.POST
    )
    ResponseEntity<?> createDocumentFromFile(
        @ApiParam(value = "file", required = true) MultipartFile file,
        @CurrentUser User user
    ) throws IOException;

    @ApiOperation(
        value = "Get current user documents",
        response = List.class
    )
    @RequestMapping(value = "/me", method = RequestMethod.GET)
    ResponseEntity<?> listMe(Page page, @CurrentUser User currentUser);
}


package cu.vlired.esFacilCore.api;

import cu.vlired.esFacilCore.dto.DocumentDTO;
import cu.vlired.esFacilCore.model.Document;
import cu.vlired.esFacilCore.model.User;
import cu.vlired.esFacilCore.dto.UserDTO;
import cu.vlired.esFacilCore.security.CurrentUser;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import java.io.IOException;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Api(value = "Documents", tags = {"Document"})
@RequestMapping("/document")
public interface DocumentApi {

    @RequestMapping(method = RequestMethod.POST)
    ResponseEntity<Document> createDocument(@ApiParam(value = "", required = true) @RequestBody Document document);

    @RequestMapping(method = RequestMethod.GET)
    ResponseEntity<List<Document>> getAllDocuments();

    @RequestMapping(value = "/search", method = RequestMethod.GET)
    ResponseEntity<List<Document>> searchDocument(@ApiParam(value = "", required = true) @RequestParam String term);

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    ResponseEntity<?> deleteDocument(@PathVariable long document_id);

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
}


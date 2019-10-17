/*
 * @copyleft VLIRED
 * @author jose
 * 10/13/19
 */
package cu.vlired.submod.api;

import cu.vlired.submod.model.Document;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Api(value = "Files", tags = "File")
@RequestMapping("/file")
public interface FilesApi {

    @RequestMapping(value = "/create-doc-from-file", headers = ("content-type=multipart/*"), method = RequestMethod.POST)
    ResponseEntity<Document> CreateDocFromFile(
            @ApiParam(value = "file", required = true) MultipartFile file
    ) throws IOException;

    @RequestMapping(value = "/create-document-from-file", headers = ("content-type=multipart/*"), method = RequestMethod.POST)
    ResponseEntity<Document> CreateDocumentFromFile(
            @ApiParam(value = "file", required = true) MultipartFile file
    ) throws IOException;
}

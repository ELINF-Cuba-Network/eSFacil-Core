/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cu.vlired.submod.api;

import cu.vlired.submod.model.Bitstream;
import cu.vlired.submod.model.Document;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

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

    @RequestMapping(value = "/fromfile", method = RequestMethod.POST)
    ResponseEntity<Document> createDocumentfromFile(@ApiParam(value = "", required = true) @RequestBody Bitstream bitstream);
}


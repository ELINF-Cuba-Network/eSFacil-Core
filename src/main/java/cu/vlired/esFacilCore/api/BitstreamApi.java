package cu.vlired.esFacilCore.api;

import cu.vlired.esFacilCore.model.Bitstream;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;


@Api(value = "Bitstreams", tags = {"Bitstream"})
public interface BitstreamApi {

    @ApiOperation(
        value = "Attach bitstream to a document",
        response = Bitstream.class
    )
    @RequestMapping(
        value = "/document/{document}/bitstream",
        headers = ("content-type=multipart/*"),
        method = RequestMethod.POST
    )
    ResponseEntity<?> uploadBitstream(
        @ApiParam(required = true) @PathVariable UUID document,
        @ApiParam(required = true) @RequestParam("file") MultipartFile file,
        @ApiParam() @RequestParam("description") String description
    ) throws IOException;

    @ApiOperation(
        value = "Attach bitstreams to a document",
        response = Bitstream.class
    )
    @RequestMapping(
        value = "/document/{document}/bitstreams",
        headers = ("content-type=multipart/*"),
        method = RequestMethod.POST
    )
    ResponseEntity<?> uploadBitstreams(
        @ApiParam(required = true) @PathVariable UUID document,
        @ApiParam(required = true) @RequestParam("file") MultipartFile[] files,
        @ApiParam() @RequestParam("description") String description
    ) throws IOException;

    @ApiOperation(
        value = "Download bitstream",
        response = MultipartFile.class
    )
    @RequestMapping(
        value = "/bitstream/{bitstream}/download",
        method = RequestMethod.GET
    )
    byte[] downloadBitstream(
        @ApiParam(required = true) @PathVariable UUID bitstream
    ) throws IOException;

    @ApiOperation(
        value = "Delete bitstream"
    )
    @RequestMapping(
        value = "/bitstream/{bitstream}",
        method = RequestMethod.DELETE
    )
    ResponseEntity<?> deleteBitstream(
        @PathVariable UUID bitstream
    );

    @ApiOperation(
        value = "Delete bitstreams"
    )
    @RequestMapping(
        value = "/bitstreams",
        method = RequestMethod.DELETE
    )
    ResponseEntity<?> deleteBitstreams(
        @RequestBody @Valid UUID[] bitstreams
    );

}
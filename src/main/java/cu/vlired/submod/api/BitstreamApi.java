/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cu.vlired.submod.api;

import cu.vlired.submod.model.Bitstream;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


/**
 * @author luizo
 */
@Api(value = "Bitstreams", tags = {"Bitstream"})
@RequestMapping("/bitstream")
public interface BitstreamApi {

    @RequestMapping(value = "/", method = RequestMethod.POST)
    ResponseEntity<Bitstream> createBitstream(@ApiParam(value = "", required = true) @RequestBody Bitstream bitstream);

    @RequestMapping(value = "/batch", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    ResponseEntity<List<Bitstream>> batchCreateBitstream(@ApiParam(value = "", required = true) @RequestBody Bitstream[] bitstream);

    @RequestMapping(value = "/batch", method = RequestMethod.DELETE)
    ResponseEntity<?> batchDeleteBitstream(@ApiParam(value = "", required = true) @RequestParam String[] ids);

    @RequestMapping(value = "/", method = RequestMethod.GET)
    ResponseEntity<List<Bitstream>> getAllBitstreams();

    @RequestMapping(value = "/search", method = RequestMethod.GET)
    ResponseEntity<List<Bitstream>> searchBitstream(@ApiParam(value = "", required = true) @RequestParam String term);

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    ResponseEntity<?> deleteBitstream(@PathVariable long bitstream_id);

}
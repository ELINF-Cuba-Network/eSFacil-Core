/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cu.vlired.esFacilCore.api;

import cu.vlired.esFacilCore.model.Bitstream;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


/**
 *
 * @author luizo
 */
 @Api( value = "Bitstreams", tags = {"Bitstream"})
    public interface BitstreamApi {

        @RequestMapping(value = "/bitstream", method = RequestMethod.POST)
        ResponseEntity<Bitstream> createBitstream(@ApiParam(value = "", required = true) @RequestBody Bitstream bitstream);

        @RequestMapping(value = "/bitstreams", method = RequestMethod.GET)
        ResponseEntity<List<Bitstream>>  getAllBitstreams();

        @RequestMapping(value = "/bitstream/search", method = RequestMethod.GET)
        ResponseEntity<List<Bitstream>>  searchBitstream(@ApiParam(value = "", required = true) @RequestParam String term);
        
        @RequestMapping(value = "/bitstream/{id}", method = RequestMethod.DELETE)
        ResponseEntity<?> deleteBitstream(@PathVariable long bitstream_id);

 }
package cu.vlired.submod.api;

import cu.vlired.submod.components.ResponsesHelper;
import cu.vlired.submod.model.Bitstream;
import cu.vlired.submod.repository.BitstreamRepository;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BitstreamApiController implements BitstreamApi {

    @Value("${dir.assetstore}")
    private String dir_assetstore;

    private BitstreamRepository bitstreamRepository;
    private ResponsesHelper responseHelper;

    public BitstreamApiController(
            BitstreamRepository bitstreamRepository,
            ResponsesHelper responseHelper
    ) {
        this.bitstreamRepository = bitstreamRepository;
        this.responseHelper = responseHelper;
    }

    @Override
    public ResponseEntity<Bitstream> createBitstream(@RequestBody Bitstream bitstream) {

        String Dir_File = dir_assetstore.concat("/LuizoFile.txt");

        File file = new File(Dir_File);
        try {
            file.createNewFile();
        } catch (IOException ex) {
            Logger.getLogger(BitstreamApiController.class.getName()).log(Level.SEVERE, null, ex);
        }
        bitstream.setName(Dir_File);
        Bitstream saveBitstream = bitstreamRepository.save(bitstream);
        return responseHelper.buildResponse(saveBitstream, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<Bitstream>> batchCreateBitstream(Bitstream[] bitstream) {
        System.out.println("...");
        List<Bitstream> result = new LinkedList<>();
        Arrays.stream(bitstream).forEach(b -> result.add(bitstreamRepository.save(b)));

        return responseHelper.ok(result);
    }

    @Override
    public ResponseEntity<?> batchDeleteBitstream(String[] ids) {
        Arrays.stream(ids).forEach(b -> bitstreamRepository.deleteById(UUID.fromString(b)));
        return responseHelper.okNoData();
    }

    @Override
    public ResponseEntity<List<Bitstream>> getAllBitstreams() {
        List<Bitstream> listBitstreams = bitstreamRepository.findAll();
        return ResponseEntity.ok().body(listBitstreams);
    }

    @Override
    public ResponseEntity<List<Bitstream>> searchBitstream(String term) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ResponseEntity<?> deleteBitstream(long bitstreamt_id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
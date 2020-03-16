package cu.vlired.esFacilCore.controller;

import cu.vlired.esFacilCore.api.BitstreamApi;
import cu.vlired.esFacilCore.components.ResponsesHelper;
import cu.vlired.esFacilCore.dto.BitstreamDTO;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import cu.vlired.esFacilCore.services.BitstreamService;
import org.apache.commons.io.IOUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

@RestController
@EnableTransactionManagement
public class BitstreamApiController implements BitstreamApi {

    private final BitstreamService bitstreamService;
    private ResponsesHelper responseHelper;

    public BitstreamApiController(
        BitstreamService bitstreamService,
        ResponsesHelper responseHelper
    ) {
        this.bitstreamService = bitstreamService;
        this.responseHelper = responseHelper;
    }

    @Override
    public ResponseEntity<?> uploadBitstream(UUID document, MultipartFile file, String description)
        throws IOException {

        BitstreamDTO bitstream = bitstreamService.create(document, file, description);
        return responseHelper.buildResponse(bitstream, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> uploadBitstreams(
        UUID document,
        MultipartFile[] files,
        String description) throws IOException {

        List<BitstreamDTO> bitstreamDTOS = bitstreamService.batchCreate(document, files, description);
        return responseHelper.buildResponse(bitstreamDTOS, HttpStatus.OK);
    }

    @Override
    public byte[] downloadBitstream(UUID bitstream) throws IOException {
        BitstreamDTO bitstreamDTO = bitstreamService.findById(bitstream);
        String code = bitstreamDTO.getCode();

        String path = bitstreamService.getPathByBitstreamCode(code);

        FileInputStream fis = new FileInputStream(path);
        return IOUtils.toByteArray(fis);
    }

    @Override
    public ResponseEntity<?> deleteBitstream(UUID bitstream) {
        bitstreamService.deleteById(bitstream);
        return responseHelper.okNoData();
    }

    @Override
    @Transactional
    public ResponseEntity<?> deleteBitstreams(@Valid UUID[] bitstreams) {
        Arrays.asList(bitstreams)
            .forEach(bitstreamService::deleteById);

        return responseHelper.okNoData();
    }

}
package cu.vlired.esFacilCore.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import cu.vlired.esFacilCore.components.ResponsesHelper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class InputJsonApiController {
 
    @Value("${dir.config}")
    private String dir_config;

    @Value("${app.input-json-file}")
    private String inputJsonFile;

    private ResponsesHelper responseHelper;

    public InputJsonApiController(ResponsesHelper responseHelper) {
        this.responseHelper = responseHelper;
    }

    @GetMapping(value = "/inputJson")
    public ResponseEntity InputJson() throws IOException{
        
        byte[] mapData = Files.readAllBytes(
                Paths.get(dir_config, inputJsonFile)
        );

        ObjectMapper objectMapper = new ObjectMapper();
        HashMap myMap = objectMapper.readValue(mapData, HashMap.class);
        
        return responseHelper.ok(myMap);
    }
    
}
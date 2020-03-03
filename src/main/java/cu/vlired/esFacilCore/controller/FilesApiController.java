package cu.vlired.esFacilCore.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import cu.vlired.esFacilCore.components.ResponsesHelper;
import cu.vlired.esFacilCore.constants.Condition;
import cu.vlired.esFacilCore.model.Bitstream;
import cu.vlired.esFacilCore.model.Document;
import cu.vlired.esFacilCore.repository.DocumentRepository;
import cu.vlired.esFacilCore.services.BitstreamService;
import cu.vlired.esFacilCore.services.MetadataResolver;
import cu.vlired.esFacilCore.services.XMLService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class FilesApiController {

    private BitstreamService bitstreamService;
    private XMLService xmlService;
    private ResponsesHelper responseHelper;
    private MetadataResolver metadataResolver;
    private DocumentRepository documentRepository;

    @Value("${dir.config}")
    private String dir_config;

    public FilesApiController(
            BitstreamService bitstreamService,
            XMLService xmlService,
            ResponsesHelper responseHelper,
            MetadataResolver metadataResolver,
            DocumentRepository documentRepository
    ) {
        this.bitstreamService = bitstreamService;
        this.xmlService = xmlService;
        this.responseHelper = responseHelper;
        this.metadataResolver = metadataResolver;
        this.documentRepository = documentRepository;
    }

    //Servicio para crear ficheros
    @RequestMapping(value = "/createfiles", headers = ("content-type=multipart/*"))
    public ResponseEntity CreateFiles(
            @RequestParam(value = "file") MultipartFile file
    ) throws IOException {

        if (bitstreamService.createBitstreamFromFile(file) != null)
            return responseHelper.ok("Creado");
        else
            return responseHelper.ok("No Creado");
    }

    //Servicio consumir JSON de Crossref
    @RequestMapping(value = "/jsonbydoi")
    public ResponseEntity JsonByDOI(
            @RequestParam(value = "doi") String doi) throws IOException {
        HashMap<String, Object> jsonCsl = new HashMap();

        String base_url = "https://api.crossref.org/v1/works/http://dx.doi.org/";
        String url = base_url + doi;
        RestTemplate restTemplate = new RestTemplate();
        HashMap<String, Object> json_doi = new HashMap<>();
        json_doi = restTemplate.getForObject(url, HashMap.class, json_doi);

        byte[] mapData = Files.readAllBytes(Paths.get(dir_config.concat("/CrossrefApiToCSLMap.json")));
        Map<String, String> jsonConfigMap = new HashMap<String, String>();
        ObjectMapper objectMapper = new ObjectMapper();
        jsonConfigMap = objectMapper.readValue(mapData, HashMap.class);

        HashMap<String, Object> message = (HashMap<String, Object>) json_doi.get("message");

        for (Map.Entry<String, String> entrySet : jsonConfigMap.entrySet()) {
            String key_CSL = entrySet.getKey();
            String value_DOIApi = entrySet.getValue();
            //Guardo como llave la llave del JSON de Configuracion(CrossrefApiToCSLMap.json)
            //Guardo como value el valor que esta en el JSON del DOI API-RESTm usando como llave de busqueda
            //el value de CrossrefApiToCSLMap.json
            jsonCsl.put(key_CSL, message.get(value_DOIApi));
        }


        return responseHelper.buildResponse(jsonCsl, HttpStatus.OK);
    }

}

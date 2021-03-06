package cu.vlired.submod.api;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import cu.vlired.submod.components.ResponsesHelper;
import cu.vlired.submod.constants.Condition;
import cu.vlired.submod.model.Bitstream;
import cu.vlired.submod.model.Document;
import cu.vlired.submod.repository.DocumentRepository;
import cu.vlired.submod.services.BitstreamService;
import cu.vlired.submod.services.DarkaivMetadataResolverImp;
import cu.vlired.submod.services.MetadataResolver;
import cu.vlired.submod.services.XMLService;

import java.io.File;
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
public class FilesApiController implements FilesApi {

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

    /**
     * Get a Multipart file and convert it to a Document
     * with this multipart file as bitstream
     * All document data are empty because
     * this method dont use any third party service
     *
     * @param file - The multipart file
     * @return - The created document
     * @throws IOException - On IO Error
     */
    public ResponseEntity<Document> CreateDocFromFile(
        @RequestParam(value = "file") MultipartFile file
    ) throws IOException {

        // TODO. Handle this
        return responseHelper.ok(new Document());
    }

    /**
     * 
     * @param file
     * @return
     * @throws IOException
     */
    public ResponseEntity<Document> CreateDocumentFromFile(
            @RequestParam(value = "file") MultipartFile file
    ) throws IOException {

        // Create a bitstream using the file
        Bitstream bitstream = bitstreamService.createBitstreamFromFile(file);

        // Getting the bitstream metadata from Darkaiv
        Map<Object, Object> jsonDarkaiv = metadataResolver.getMetadataFromFile(file);

        // Create the document
        Document doc = new Document();
        doc.setData(jsonDarkaiv);
        doc.addBitstream(bitstream);

        // By default when submit a new file is in process
        doc.setCondition(Condition.IN_PROCESS);
        documentRepository.save(doc);

        return responseHelper.ok(doc);
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

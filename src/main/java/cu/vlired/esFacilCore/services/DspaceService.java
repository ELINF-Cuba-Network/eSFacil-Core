package cu.vlired.esFacilCore.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import cu.vlired.esFacilCore.dto.dspace.ItemDTO;
import cu.vlired.esFacilCore.model.SubmitTask;
import cu.vlired.esFacilCore.model.documentData.DocumentData;
import cu.vlired.esFacilCore.repository.BitstreamRepository;
import cu.vlired.esFacilCore.util.DSpaceURLHelper;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.io.FileUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.*;

@Service
@Log4j2
public class DspaceService {

    final
    RestTemplate http;
    private final BitstreamRepository bitstreamRepository;
    private final BitstreamService bitstreamService;

    public DspaceService(RestTemplate http,
                         BitstreamRepository bitstreamRepository,
                         BitstreamService bitstreamService
    ) {
        this.http = http;
        this.bitstreamRepository = bitstreamRepository;
        this.bitstreamService = bitstreamService;
    }

    public String login(String email, String password, String url) {
        var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);


        var body = new LinkedMultiValueMap<String, String>();
        body.add("email", email);
        body.add("password", password);

        var request = new HttpEntity<MultiValueMap<String, String>>(body, headers);

        ResponseEntity<Void> responseEntity = this.http.exchange(
            url,
            HttpMethod.POST,
            request,
            Void.class
        );

        return responseEntity.getHeaders().getFirst(HttpHeaders.SET_COOKIE);
    }

    public ItemDTO postItem(String cookie, SubmitTask submitTask) throws IOException, IllegalAccessException {
        var document = submitTask.getDocument();
        var config = submitTask.getConfig();

        var urlHelper = new DSpaceURLHelper(config.getData());
        var url = urlHelper.buildPostItemURL();

        var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Cookie", cookie);


        HashMap<String, LinkedList<HashMap<String, String>>> body =
            buildItemBody(document.getData());

        var mapper = new ObjectMapper();
        var jsonBody = mapper.writeValueAsString(body);

        var request = new HttpEntity<>(jsonBody, headers);

        ResponseEntity<ItemDTO> responseEntity =
            this.http.postForEntity(url, request, ItemDTO.class);

        return responseEntity.getBody();
    }

    public void postBitstream(String cookie, String url, File file) throws IOException {
        var array = FileUtils.readFileToByteArray(file);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.add("Cookie", cookie);

        var requestEntity = new HttpEntity<>(array, headers);

        log.info("Starting POST Bitstream");

        var res = this.http.exchange(
            url,
            HttpMethod.POST,
            requestEntity,
            Object.class
        );

        log.info("POST Bitstream response " + res.toString());
    }

    private HashMap<String, LinkedList<HashMap<String, String>>>
    buildItemBody(DocumentData data) throws IOException, IllegalAccessException {

        var dataClass = data.getClass();
        var fields = dataClass.getDeclaredFields();

        HashMap mapper = new ObjectMapper().readValue(
            new ClassPathResource("submodconfig/DSpaceMap.json")
                .getInputStream(),
            HashMap.class
        );

        var metadataList = new LinkedList<HashMap<String, String>>();

        for (Field f : fields) {
            f.setAccessible(true);
            var key = mapper.get(f.getName());

            if (key == null) {
                continue;
            }

            var m = new HashMap<String, String>();
            var list = (List<String>) f.get(data);

            if (list == null) {
                continue;
            }

            for (Object v : list) {
                m.put("key", (String) key);
                m.put("value", v.toString());

                metadataList.add(m);
            }
        }

        var body = new HashMap<String, LinkedList<HashMap<String, String>>>();
        body.put("metadata", metadataList);

        return body;
    }

    public String test() {
        ResponseEntity<String> responseEntity =
            this.http.getForEntity("http://bedb2f752fce:8080/rest/test", String.class);

        return responseEntity.getBody();
    }
}

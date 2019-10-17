/*
 * @copyleft VLIRED
 * @author jose
 * 10/13/19
 */
package cu.vlired.submod.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class DarkaivService {

    private static final String PATH = "/document/workflow/test2";

    @Value("${dir.darkaiv}")
    private String dir_darkaiv;

    private RestTemplate restTemplate;

    public DarkaivService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Map<String, List<String>> getMetadataFromFile() {

        // TODO: Change this when Darkaiv works
        // Map resp = restTemplate.getForObject(dir_darkaiv + PATH, HashMap.class);

        Map<String, List<String>> resp = new HashMap<>();

        List<String> al = Arrays.asList("de Tobias, El Bueno", "Hdez, Jose Javier");
        List<String> tl = Arrays.asList("Titulo 1", "Titulo 2");

        resp.put("title", tl);
        resp.put("author", al);

        return resp;

    }

}

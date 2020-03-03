/*
 * @copyleft VLIRED
 * @author jose
 * 10/13/19
 */
package cu.vlired.esFacilCore.services;

import cu.vlired.esFacilCore.model.documentData.DocumentData;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@Service
public class DarkaivMetadataResolverImp implements MetadataResolver {

    private static final String PATH = "/document/workflow/test2";

    @Value("${dir.darkaiv}")
    private String dir_darkaiv;

    private RestTemplate restTemplate;

    public DarkaivMetadataResolverImp(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public DocumentData getMetadataFromFile(MultipartFile file) {

        // TODO: Change this when Darkaiv works
        // Map resp = restTemplate.getForObject(dir_darkaiv + PATH, HashMap.class);

        return DocumentData.builder().build();
    }

    @Override
    public Map<String, List<String>> processResponse(Object data) {
        return null;
    }
}

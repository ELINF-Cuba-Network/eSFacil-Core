/*
 * @copyleft VLIRED
 * @author jose
 * 10/17/19
 */
package cu.vlired.submod.services;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Primary
public class DummyMetadataResolver implements MetadataResolver {

    @Override
    public Map<String, List<String>> getMetadataFromFile(MultipartFile file) {

        Map<String, String> data = new HashMap<String, String>() {{
            put("title", "Un Chino muy chino");
            put("author", "de Tobias, El Bueno");
            put("publisher", "Loulogio");
            put("type", "Journal");
            put("issued", "2001");
        }};

        return processResponse(data);
    }

    @Override
    public Map<String, List<String>> processResponse(Object data) {

        Map<String, String> given = (Map<String, String>) data;

        Map<String, List<String>> resp = new HashMap<>();

        for (String key: given.keySet()) {
            resp.put(key, Arrays.asList(given.get(key)));
        }

        return resp;
    }
}

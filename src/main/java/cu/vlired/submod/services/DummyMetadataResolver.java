/*
 * @copyleft VLIRED
 * @author jose
 * 10/17/19
 */
package cu.vlired.submod.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Primary
public class DummyMetadataResolver implements MetadataResolver {

    @Value("${dir.config}")
    private String dir_config;

    @Override
    public Map<Object, Object> getMetadataFromFile(MultipartFile file) {

        byte[] mapData = new byte[0];
        Map<Object, Object> map = new HashMap<Object, Object>();

        try {
            mapData = Files.readAllBytes(
                    Paths.get(dir_config, "ModelData.json")
            );

            ObjectMapper objectMapper = new ObjectMapper();
            map = objectMapper.readValue(mapData, Map.class);

            return map;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return map;
    }

    @Override
    public Map<Object, Object> processResponse(Object data) {

        Map<Object, Object> given = (Map<Object, Object>) data;
        return given;
    }
}

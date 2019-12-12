/*
 * @copyleft VLIRED
 * @author jose
 * 10/17/19
 */
package cu.vlired.submod.services;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;
import java.util.Map;

public interface MetadataResolver {

    /**
     * Extract metadata of the given file
     *
     * @param file File to extract metadata
     * @return Map with Metadata => [...values]
     */
    public Map<Object, Object> getMetadataFromFile(MultipartFile file);

    /**
     * Process a response for a external metadata extractor service
     * like Darkaiv and return a Map like 'metadata_key' => [...values]
     *
     * @param data The raw metadata taken from a generic source e.x: Darkaiv
     * @return Map with Metadata => [...values]
     */
    public Map<Object, Object> processResponse(Object data);

}

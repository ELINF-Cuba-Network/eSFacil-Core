/*
 * @copyleft VLIRED
 * @author jose
 * 10/13/19
 */
package cu.vlired.esFacilCore.services;

import cu.vlired.esFacilCore.exception.StorageException;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
@Log4j2
public class FSStorageServiceImpl implements StorageService {

    @Value("${dir.assetstore}")
    private String dir_assetstore;

    @Override
    public void store(MultipartFile file, String name) throws IOException {

        log.info("Coping file to " + dir_assetstore);

        if (file.isEmpty()) {
            throw new StorageException("Impossible store an empty file.");
        }

        var f = new File(dir_assetstore + File.separator + name);

        if (!f.exists()) {
            log.info(String.format("Creating new file? %b", f.createNewFile()));
        }

        try (InputStream inputStream = file.getInputStream()) {
            Files.copy(inputStream, Paths.get(dir_assetstore, name),
                    StandardCopyOption.REPLACE_EXISTING);
        }

    }

}

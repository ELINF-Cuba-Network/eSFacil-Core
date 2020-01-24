/*
 * @copyleft VLIRED
 * @author jose
 * 10/13/19
 */
package cu.vlired.esFacilCore.services;

import cu.vlired.esFacilCore.exception.StorageException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class FSStorageServiceImpl implements StorageService {

    @Value("${dir.assetstore}")
    private String dir_assetstore;

    @Override
    public void store(MultipartFile file, String name) throws IOException {

        if (file.isEmpty()) {
            throw new StorageException("Impossible store an empty file.");
        }

        try (InputStream inputStream = file.getInputStream()) {
            Files.copy(inputStream, Paths.get(dir_assetstore, name),
                    StandardCopyOption.REPLACE_EXISTING);
        }

    }

}

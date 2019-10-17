/*
 * @copyleft VLIRED
 * @author jose
 * 10/13/19
 */
package cu.vlired.submod.services;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface StorageService {

    void store(MultipartFile file, String name) throws IOException;

}

package cu.vlired.submod.services;

import cu.vlired.submod.model.Bitstream;
import cu.vlired.submod.repository.BitstreamRepository;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class BitstreamService {

    @Value("${dir.assetstore}")
    private String dir_assetstore;

    private BitstreamRepository bitstreamRepository;
    private StorageService storageService;

    public BitstreamService(
        BitstreamRepository bitstreamRepository,
        StorageService storageService
    ) {
        this.bitstreamRepository = bitstreamRepository;
        this.storageService = storageService;
    }

    /**
     * Create a bitstream from a Multipart File
     *
     * @param file The Multipart File
     * @return The created bitstream
     * @throws IOException Exception
     */
    public Bitstream createBitstreamFromFile(MultipartFile file) throws IOException
    {
        String name = file.getOriginalFilename();
        String code = UUID.randomUUID().toString();

        String storeName = code + ".bin";

        // Store the new file
        storageService.store(file, storeName);

        // Create bitstream
        String shortName = FilenameUtils.getBaseName(name);
        String extension = FilenameUtils.getExtension(name);
        Bitstream bitstream = new Bitstream(shortName, extension, code);
        bitstreamRepository.save(bitstream);

        return bitstream;
    }
    
    public boolean CreateBitstreamForDarkaiv(MultipartFile file) throws IOException
    {
        String name = file.getOriginalFilename();
        String code = UUID.randomUUID().toString();
        File temp = new File("/home/luizo/Desktop/DocTest.pdf");
        file.transferTo(temp);
        temp.createNewFile();
        boolean is_created =  temp.exists();
        if (is_created) {
            String short_name = FilenameUtils.getBaseName(name);
            String extension = FilenameUtils.getExtension(name);
            Bitstream bitstream = new Bitstream(short_name, extension, code);
            bitstreamRepository.save(bitstream);
        }
        return is_created;
    }
    
}

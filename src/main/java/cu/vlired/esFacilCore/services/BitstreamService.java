package cu.vlired.esFacilCore.services;

import cu.vlired.esFacilCore.components.I18n;
import cu.vlired.esFacilCore.dto.BitstreamDTO;
import cu.vlired.esFacilCore.exception.ResourceNotFoundException;
import cu.vlired.esFacilCore.model.Bitstream;
import cu.vlired.esFacilCore.model.Document;
import cu.vlired.esFacilCore.repository.BitstreamRepository;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import cu.vlired.esFacilCore.repository.DocumentRepository;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.apache.commons.io.FilenameUtils;

@Service
@Log4j2
@EnableTransactionManagement
public class BitstreamService {

    @Value("${dir.assetstore}")
    private String dir_assetstore;

    private BitstreamRepository bitstreamRepository;
    private StorageService storageService;
    private final I18n i18n;
    private final DocumentRepository documentRepository;
    private final DTOUtilService dtoUtilService;

    public BitstreamService(
        BitstreamRepository bitstreamRepository,
        StorageService storageService,
        I18n i18n,
        DocumentRepository documentRepository,
        DTOUtilService dtoUtilService
    ) {
        this.bitstreamRepository = bitstreamRepository;
        this.storageService = storageService;
        this.i18n = i18n;
        this.documentRepository = documentRepository;
        this.dtoUtilService = dtoUtilService;
    }

    public BitstreamDTO create(UUID documentId, MultipartFile file, String description) throws IOException {
        Document document = documentRepository
            .findById(documentId)
            .orElseThrow(() -> new ResourceNotFoundException(
                i18n.t("app.document.id.not.found", ArrayUtils.toArray(documentId))
        ));

        log.info("Document " + document);

        Bitstream bitstream = createBitstreamFromFile(file);
        bitstream.setDocument(document);
        bitstream.setDescription(description);

        bitstreamRepository.save(bitstream);
        return dtoUtilService.convertToDTO(bitstream, BitstreamDTO.class);
    }

    @Transactional
    public List<BitstreamDTO> batchCreate(UUID documentId, MultipartFile[] files, String description) throws IOException {
        Document document = documentRepository
            .findById(documentId)
            .orElseThrow(() -> new ResourceNotFoundException(
                i18n.t("app.document.id.not.found", ArrayUtils.toArray(documentId))
            ));

        List<Bitstream> bitstreams = new LinkedList<>();

        for (MultipartFile multipartFile : Arrays.asList(files)) {
            Bitstream bitstream = createBitstreamFromFile(multipartFile);

            bitstream.setDocument(document);
            bitstream.setDescription(description);
            bitstreamRepository.save(bitstream);

            bitstreams.add(bitstream);
        }

        return bitstreams
            .stream()
            .map(b -> dtoUtilService.convertToDTO(b, BitstreamDTO.class))
            .collect(Collectors.toList());
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
        log.info("Creating File");

        String name = file.getOriginalFilename();
        String code = UUID.randomUUID().toString();

        log.info("Name: " + name);
        log.info("Code: " + code);

        // Store the new file
        storageService.store(file, code);

        // Create bitstream
        String shortName = FilenameUtils.getBaseName(name);
        String extension = FilenameUtils.getExtension(name);
        Bitstream bitstream = new Bitstream(shortName, extension, code);
        bitstreamRepository.save(bitstream);

        return bitstream;
    }

    public BitstreamDTO findById(UUID bitstreamId) {
        Bitstream bitstream = bitstreamRepository.findById(bitstreamId)
            .orElseThrow(() -> new ResourceNotFoundException(
                i18n.t("app.bitstream.id.not.found", ArrayUtils.toArray(bitstreamId))
            ));

        return dtoUtilService.convertToDTO(bitstream, BitstreamDTO.class);
    }

    public void deleteById(UUID bitstreamId) {
        Bitstream bitstream = bitstreamRepository.findById(bitstreamId)
            .orElseThrow(
                () -> new ResourceNotFoundException(i18n.t("app.bitstream.id.not.found", ArrayUtils.toArray(bitstreamId)))
            );

        deleteBitstreamBinaryByCode(bitstream.getCode());
        bitstreamRepository.deleteById(bitstreamId);
    }


    public void deleteBitstreamBinaryByCode(String code) {
        File file = new File(getPathByBitstreamCode(code));

        if (file.exists()) {
            try {
                file.delete();
            } catch (Exception ex) {
                log.error(ExceptionUtils.getStackTrace(ex));
            }
        }
    }

    public String getPathByBitstreamCode(String code) {
        var base = new File(dir_assetstore);
        var path = new File(base, code);

        return path.getPath();
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

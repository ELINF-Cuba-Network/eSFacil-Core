package cu.vlired.esFacilCore.services;

import cu.vlired.esFacilCore.components.I18n;
import cu.vlired.esFacilCore.constants.Condition;
import cu.vlired.esFacilCore.constants.Roles;
import cu.vlired.esFacilCore.dto.UserDTO;
import cu.vlired.esFacilCore.dto.documentData.DocumentDataDTO;
import cu.vlired.esFacilCore.exception.ResourceNotFoundException;
import cu.vlired.esFacilCore.model.Bitstream;
import cu.vlired.esFacilCore.model.Document;
import cu.vlired.esFacilCore.model.documentData.DocumentData;
import cu.vlired.esFacilCore.model.User;
import cu.vlired.esFacilCore.dto.DocumentDTO;
import cu.vlired.esFacilCore.repository.DocumentRepository;
import cu.vlired.esFacilCore.util.Page;
import org.apache.commons.lang3.ArrayUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class DocumentService {

    @Value("${dir.config}")
    private String dir_config;

    private final BitstreamService bitstreamService;
    private final DocumentRepository documentRepository;
    private final DTOUtilService dtoUtilService;
    private final PaginateService paginateService;
    private final I18n i18n;
    private final MetadataResolver metadataResolver;

    public DocumentService(
        BitstreamService bitstreamService,
        DocumentRepository documentRepository,
        DTOUtilService dtoUtilService,
        PaginateService paginateService,
        I18n i18n,
        MetadataResolver metadataResolver
    ) {
        this.bitstreamService = bitstreamService;
        this.documentRepository = documentRepository;
        this.dtoUtilService = dtoUtilService;
        this.paginateService = paginateService;
        this.i18n = i18n;
        this.metadataResolver = metadataResolver;
    }

    public DocumentDTO createFromData(DocumentDataDTO dataDTO, User user) {
        DocumentData data = dtoUtilService.convertToPOJO(dataDTO, DocumentData.class);

        Document document = new Document();
        document.setData(data);
        document.setPerson(user);

        Document newDocument = documentRepository.save(document);
        return dtoUtilService.convertToDTO(newDocument, DocumentDTO.class);
    }

    public List<DocumentDTO> list(Page page) {
        paginateService.preProcess(page);
        List<Document> list = documentRepository.list(page);

        return list.stream()
            .map(document -> dtoUtilService.convertToDTO(document, DocumentDTO.class))
            .collect(Collectors.toList());
    }

    public List<DocumentDTO> listMe(Page page, User user) {
        paginateService.preProcess(page);
        List<Document> list = documentRepository.listMe(page, user);

        return list.stream()
            .map(document -> dtoUtilService.convertToDTO(document, DocumentDTO.class))
            .collect(Collectors.toList());
    }

    public DocumentDTO getById(UUID id) {
        Document document = documentRepository
            .findById(id)
            .orElseThrow(() -> new ResourceNotFoundException(i18n.t("app.document.id.not.found", ArrayUtils.toArray(id))));

        return dtoUtilService.convertToDTO(document, DocumentDTO.class);
    }

    public void deleteById(UUID id) {
        if (!documentRepository.existsById(id)) {
            throw new ResourceNotFoundException(i18n.t("app.document.id.not.found", ArrayUtils.toArray(id)));
        }

        documentRepository.deleteById(id);
    }

    public DocumentDTO update(UUID id, DocumentDataDTO dataDTO) {
        Document document = documentRepository
            .findById(id)
            .orElseThrow(() -> new ResourceNotFoundException(i18n.t("app.document.id.not.found", ArrayUtils.toArray(id))));


        DocumentData data = dtoUtilService.convertToPOJO(dataDTO, DocumentData.class);
        document.setData(data);

        Document newDocument = documentRepository.save(document);
        return dtoUtilService.convertToDTO(newDocument, DocumentDTO.class);
    }

    public DocumentDTO patch(UUID id, DocumentDataDTO dataDTO) {
        Document document = documentRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException(
                i18n.t("app.security.user.id.not.found", ArrayUtils.toArray(id))
            ));

        var modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setSkipNullEnabled(true);

        DocumentData data = document.getData();
        modelMapper.map(dataDTO, data);

        document.setData(data);

        Document newDocument = documentRepository.save(document);
        return dtoUtilService.convertToDTO(newDocument, DocumentDTO.class);
    }

    public DocumentDTO createFromFile(MultipartFile file, User user) throws IOException {
        // Create a bitstream using the file
        Bitstream bitstream = bitstreamService
            .createBitstreamFromFile(file);

        // Getting the bitstream metadata from Darkaiv
        DocumentData documentData = metadataResolver
            .getMetadataFromFile(file);

        // I need to Map the Darkaiv response to a CSL format
        // mapData has the darkaiv key to => CSL key
        // TODO: When service (Darkaiv or GROBID was ready move this to metadata resolver service)
        byte[] mapData = Files.readAllBytes(
            Paths.get(dir_config, "DarkaivApiToCSLMap.json")
        );

        // Create the document
        Document doc = new Document();
        doc.setData(documentData);
        doc.addBitstream(bitstream);
        doc.setPerson(user);

        Document document = documentRepository.save(doc);

        return dtoUtilService.convertToDTO(document, DocumentDTO.class);
    }

}

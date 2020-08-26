package cu.vlired.esFacilCore.services;

import cu.vlired.esFacilCore.components.I18n;
import cu.vlired.esFacilCore.dto.PatchSubmitConfigDTO;
import cu.vlired.esFacilCore.dto.SubmitConfigDTO;
import cu.vlired.esFacilCore.exception.ForbiddenException;
import cu.vlired.esFacilCore.exception.ResourceNotFoundException;
import cu.vlired.esFacilCore.model.SubmitConfig;
import cu.vlired.esFacilCore.model.User;
import cu.vlired.esFacilCore.repository.SubmitConfigRepository;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.ArrayUtils;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Log4j2
public class SubmitConfigService {

    private final DTOUtilService dtoUtilService;
    private final SubmitConfigRepository submitConfigRepository;
    private final I18n i18n;

    public SubmitConfigService(
        DTOUtilService dtoUtilService,
        SubmitConfigRepository submitConfigRepository,
        I18n i18n
    ) {
        this.dtoUtilService = dtoUtilService;
        this.submitConfigRepository = submitConfigRepository;
        this.i18n = i18n;
    }

    public SubmitConfigDTO create(SubmitConfigDTO submitConfigDTO, User user) {
        SubmitConfig submitConfig = dtoUtilService.convertToEntity(submitConfigDTO, SubmitConfig.class);
        submitConfig.setPerson(user);

        SubmitConfig config = submitConfigRepository.save(submitConfig);
        return dtoUtilService.convertToDTO(config, SubmitConfigDTO.class);
    }

    public SubmitConfigDTO update(UUID id, SubmitConfigDTO submitConfigDTO, User currentUser) {
        SubmitConfig oldConfig = findOldSubmitConfig(id, currentUser);

        var config = dtoUtilService.convertToEntity(submitConfigDTO, SubmitConfig.class);

        config.setId(id);
        config.setPerson(currentUser);
        config.setCreatedAt(oldConfig.getCreatedAt());

        var newConfig = submitConfigRepository.save(config);
        return dtoUtilService.convertToDTO(newConfig, SubmitConfigDTO.class);
    }

    public SubmitConfigDTO patch(UUID id, PatchSubmitConfigDTO submitConfigDTO, User currentUser) {
        SubmitConfig oldConfig = findOldSubmitConfig(id, currentUser);

        var modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setSkipNullEnabled(true);

        modelMapper.map(submitConfigDTO, oldConfig);

        SubmitConfig config = submitConfigRepository.save(oldConfig);
        return dtoUtilService.convertToDTO(config, SubmitConfigDTO.class);
    }

    private SubmitConfig findOldSubmitConfig(UUID id, User currentUser) {
        SubmitConfig oldConfig = submitConfigRepository
            .findById(id)
            .orElseThrow(() -> new ResourceNotFoundException(i18n.t("app.submit-config.id.not.found", ArrayUtils.toArray(id))));

        if (oldConfig.getPerson().getId().compareTo(currentUser.getId()) != 0) {
            throw new ForbiddenException(i18n.t("app.security.forbidden"));
        }

        return oldConfig;
    }

}

package cu.vlired.esFacilCore.controller;

import cu.vlired.esFacilCore.api.SubmitConfigApi;
import cu.vlired.esFacilCore.components.ResponsesHelper;
import cu.vlired.esFacilCore.dto.PatchSubmitConfigDTO;
import cu.vlired.esFacilCore.dto.SubmitConfigDTO;
import cu.vlired.esFacilCore.model.User;
import cu.vlired.esFacilCore.services.SubmitConfigService;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.UUID;

@Log4j2
@RestController
public class SubmitConfigApiController implements SubmitConfigApi {

    private final ResponsesHelper responsesHelper;
    private final SubmitConfigService submitConfigService;

    public SubmitConfigApiController(
        ResponsesHelper responsesHelper,
        SubmitConfigService submitConfigService
    ) {
        this.responsesHelper = responsesHelper;
        this.submitConfigService = submitConfigService;
    }

    @Override
    public ResponseEntity<?> createSubmitConfig(@Valid SubmitConfigDTO submitConfigDTO, User user) {
        SubmitConfigDTO configDTO = submitConfigService.create(submitConfigDTO, user);
        return responsesHelper.ok(configDTO);
    }

    @Override
    public ResponseEntity<?> updateSubmitConfig(UUID id, @Valid SubmitConfigDTO submitConfigDTO, User currentUser) {
        SubmitConfigDTO configDTO = submitConfigService.update(id, submitConfigDTO, currentUser);
        return responsesHelper.ok(configDTO);
    }

    @Override
    public ResponseEntity<?> patchSubmitConfig(UUID id, @Valid PatchSubmitConfigDTO submitConfigDTO, User currentUser) {
        SubmitConfigDTO configDTO = submitConfigService.patch(id, submitConfigDTO, currentUser);
        return responsesHelper.ok(configDTO);
    }
}

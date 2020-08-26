package cu.vlired.esFacilCore.api;

import cu.vlired.esFacilCore.dto.PatchSubmitConfigDTO;
import cu.vlired.esFacilCore.dto.SubmitConfigDTO;
import cu.vlired.esFacilCore.dto.documentData.DocumentDataDTO;
import cu.vlired.esFacilCore.model.User;
import cu.vlired.esFacilCore.security.CurrentUser;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import java.util.UUID;

@Api(value = "Submit config", tags = {"SubmitConfig"})
@RequestMapping("/submit-config")
public interface SubmitConfigApi {

    @ApiOperation(
        value = "Create a submit config",
        response = SubmitConfigDTO.class
    )
    @RequestMapping(method = RequestMethod.POST)
    ResponseEntity<?> createSubmitConfig(
        @ApiParam(required = true) @Valid @RequestBody SubmitConfigDTO submitConfigDTO,
        @CurrentUser User user
    );

    @ApiOperation(value = "Update a submit config")
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    ResponseEntity<?> updateSubmitConfig(
        @PathVariable("id") UUID id,
        @Valid @RequestBody SubmitConfigDTO submitConfigDTO,
        @CurrentUser User user
    );

    @ApiOperation(value = "Patch document")
    @RequestMapping(value = "/{id}", method = RequestMethod.PATCH)
    ResponseEntity<?> patchSubmitConfig(
        @PathVariable("id") UUID id,
        @Valid @RequestBody PatchSubmitConfigDTO submitConfigDTO,
        @CurrentUser User user
    );
}

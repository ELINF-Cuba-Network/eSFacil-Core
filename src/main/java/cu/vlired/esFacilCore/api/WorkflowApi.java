package cu.vlired.esFacilCore.api;

import cu.vlired.esFacilCore.dto.RejectDTO;
import cu.vlired.esFacilCore.dto.UserDTO;
import cu.vlired.esFacilCore.model.User;
import cu.vlired.esFacilCore.security.CurrentUser;
import cu.vlired.esFacilCore.validators.ConditionEdition;
import cu.vlired.esFacilCore.validators.ConditionReject;
import cu.vlired.esFacilCore.validators.ConditionReview;
import cu.vlired.esFacilCore.validators.RightReviewer;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import java.util.UUID;

@Api(value = "Documents", tags = {"Document"})
@RequestMapping("/document/workflow")
@Validated
public interface WorkflowApi {

    @ApiOperation(
        value = "Send document to process",
        notes = "After that the document can be selected by one reviewer"
    )
    @RequestMapping(
        value = "/{id}/process",
        method = RequestMethod.POST
    )
    ResponseEntity<?> processDocument(
        @ApiParam(required = true)
        @ConditionEdition
        @PathVariable UUID id
    );

    @ApiOperation(
        value = "Select document to review"
    )
    @RequestMapping(
        value = "/{id}/review",
        method = RequestMethod.POST
    )
    ResponseEntity<?> reviewDocument(
        @ApiParam(required = true)
        @ConditionReview @PathVariable UUID id,
        @CurrentUser User currentUser
    );

    @ApiOperation(
        value = "Reject document"
    )
    @RequestMapping(
        value = "/{id}/reject",
        method = RequestMethod.POST
    )
    ResponseEntity<?> rejectDocument(
        @ApiParam(required = true)
        @ConditionReject @RightReviewer @PathVariable UUID id,
        @Valid @RequestBody RejectDTO data,
        @CurrentUser User currentUser
    );

}

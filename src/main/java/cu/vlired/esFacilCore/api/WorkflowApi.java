package cu.vlired.esFacilCore.api;

import cu.vlired.esFacilCore.validators.ConditionEdition;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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

}

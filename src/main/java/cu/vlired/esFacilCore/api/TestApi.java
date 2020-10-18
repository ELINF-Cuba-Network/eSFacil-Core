package cu.vlired.esFacilCore.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@io.swagger.annotations.Api(value = "Api", tags = {"api"})
@RequestMapping("/test")
public interface TestApi {

    @ApiOperation(value = "Testing GET")
    @RequestMapping(method = RequestMethod.GET)
    ResponseEntity<?> testing() throws JsonProcessingException;

    @ApiOperation(value = "Testing Queue")
    @RequestMapping(method = RequestMethod.GET, path = "/msg")
    ResponseEntity<?> testingQueue();

    @ApiOperation(value = "Testing Strategy")
    @RequestMapping(method = RequestMethod.GET, path = "/strategy")
    ResponseEntity<?> testingStrategy();
}

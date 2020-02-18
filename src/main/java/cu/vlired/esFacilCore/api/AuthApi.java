package cu.vlired.esFacilCore.api;

import cu.vlired.esFacilCore.model.*;
import cu.vlired.esFacilCore.payload.*;
import cu.vlired.esFacilCore.payload.auth.LoginResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Api(value = "Auth", tags = {"Auth"})
@RequestMapping("/auth")
public interface AuthApi {

    @ApiOperation(value = "Sign in user", response = LoginResponse.class)
    @RequestMapping(value = "/sign-in", method = RequestMethod.POST)
    ResponseEntity<?> signIn(
            @ApiParam(value = "Username and password", required = true) @Valid @RequestBody SignInRequest data
    );

    @ApiOperation(value = "Sign up user", response = LoginResponse.class)
    @RequestMapping(value = "/sign-up", method = RequestMethod.POST)
    ResponseEntity<?> signUp(
            @ApiParam(value = "User data", required = true) @Valid @RequestBody User user
    ) throws Exception;

}

package cu.vlired.esFacilCore.api;

import cu.vlired.esFacilCore.payload.auth.LoginResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import cu.vlired.esFacilCore.model.*;
import cu.vlired.esFacilCore.payload.*;
import cu.vlired.esFacilCore.security.*;

@Api(value = "Users", tags = {"User"})
@RequestMapping("/users")
public interface UserApi {

    @RequestMapping(value = "/", method = RequestMethod.POST)
    ResponseEntity<User> createUser(@ApiParam(value = "", required = true) @RequestBody User user);

    @RequestMapping(value = "/", method = RequestMethod.GET)
    ResponseEntity<List<User>> getAllUser();

    @RequestMapping(value = "/filter", method = RequestMethod.GET)
    ResponseEntity<PagedData<User>> filterUsers(@ApiParam(value = "", required = true) @RequestParam Map<String, String> params);

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    ResponseEntity<?> deleteUser(@PathVariable UUID id);

    @RequestMapping(value = "/", method = RequestMethod.PUT)
    ResponseEntity<?> updateUser(@RequestBody User user, @CurrentUser User currentUser);

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    ResponseEntity<?> getUserById(@PathVariable UUID id);

    @RequestMapping(value = "/exist-username", method = RequestMethod.POST)
    ResponseEntity<?> existUserByUsername(@RequestBody UserExistRequest request);

    @RequestMapping(value = "/exist-email", method = RequestMethod.POST)
    ResponseEntity<?> existUserByEmail(@RequestBody UserExistRequest request);

    @RequestMapping(value = "/search", method = RequestMethod.GET)
    ResponseEntity<?> search(@RequestParam(name = "pattern") String pattern);

    @ApiOperation(value = "Get current user information", response = User.class)
    @RequestMapping(value = "/me", method = RequestMethod.GET)
    ResponseEntity<?> userStatus(@CurrentUser User currentUser);

    /**
     * Fake states response for app sidebar
     */
    @RequestMapping(value = "/user/states", method = RequestMethod.GET)
    ResponseEntity<?> getStates();

}

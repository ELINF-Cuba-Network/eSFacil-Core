package cu.vlired.esFacilCore.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import cu.vlired.esFacilCore.model.*;
import cu.vlired.esFacilCore.payload.*;
import cu.vlired.esFacilCore.security.*;

@Api(value = "Users", tags = {"User"})
public interface UserApi {

    @RequestMapping(value = "/user", method = RequestMethod.POST)
    ResponseEntity<User> createUser(@ApiParam(value = "", required = true) @RequestBody User user);

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    ResponseEntity<List<User>> getAllUser();

    @RequestMapping(value = "/user/filter", method = RequestMethod.GET)
    ResponseEntity<PagedData<User>> filterUsers(@ApiParam(value = "", required = true) @RequestParam Map<String, String> params);

    @RequestMapping(value = "/user/{id}", method = RequestMethod.DELETE)
    ResponseEntity<?> deleteUser(@PathVariable long id);

    @RequestMapping(value = "/user", method = RequestMethod.PUT)
    ResponseEntity<?> updateUser(@RequestBody User user, @CurrentUser User currentUser);

    @RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
    ResponseEntity<?> getUserById(@PathVariable long id);

    @RequestMapping(value = "/user/exist-username", method = RequestMethod.POST)
    ResponseEntity<?> existUserByUsername(@RequestBody UserExistRequest request);

    @RequestMapping(value = "/user/exist-email", method = RequestMethod.POST)
    ResponseEntity<?> existUserByEmail(@RequestBody UserExistRequest request);

    @RequestMapping(value = "/user/search", method = RequestMethod.GET)
    ResponseEntity<?> search(@RequestParam(name = "pattern") String pattern);

    /**
     * Fake states response for app sidebar
     */
    @RequestMapping(value = "/user/states", method = RequestMethod.GET)
    ResponseEntity<?> getStates();

}

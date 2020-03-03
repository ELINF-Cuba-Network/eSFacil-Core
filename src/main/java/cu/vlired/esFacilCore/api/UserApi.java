package cu.vlired.esFacilCore.api;

import cu.vlired.esFacilCore.dto.PatchUserDTO;
import cu.vlired.esFacilCore.dto.UserDTO;
import cu.vlired.esFacilCore.util.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import cu.vlired.esFacilCore.model.*;
import cu.vlired.esFacilCore.payload.*;
import cu.vlired.esFacilCore.security.*;

import javax.validation.Valid;

@Api(value = "Users", tags = {"User"})
@RequestMapping("/users")
public interface UserApi {

    @ApiOperation(value = "Create a new user", response = UserDTO.class)
    @RequestMapping(method = RequestMethod.POST)
    ResponseEntity<?> createUser(@Valid @RequestBody UserDTO user);

    @ApiOperation(value = "Get user by id", response = UserDTO.class)
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    ResponseEntity<?> getUserById(@PathVariable UUID id);

    @ApiOperation(value = "List users", response = List.class)
    @RequestMapping(method = RequestMethod.GET)
    ResponseEntity<?> listUsers(Page page);

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    ResponseEntity<?> updateUser(@PathVariable("id") UUID id, @Valid @RequestBody UserDTO user);

    @RequestMapping(value = "/{id}", method = RequestMethod.PATCH)
    ResponseEntity<?> patchUser(@PathVariable("id") UUID id, @Valid @RequestBody PatchUserDTO user);

    @RequestMapping(value = "/exist-username", method = RequestMethod.POST)
    ResponseEntity<?> existUserByUsername(@RequestBody UserExistRequest request);

    @RequestMapping(value = "/exist-email", method = RequestMethod.POST)
    ResponseEntity<?> existUserByEmail(@RequestBody UserExistRequest request);

    @ApiOperation(value = "Get current user information", response = UserDTO.class)
    @RequestMapping(value = "/me", method = RequestMethod.GET)
    ResponseEntity<?> userStatus(@CurrentUser User currentUser);

}

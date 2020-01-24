/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cu.vlired.esFacilCore.api;

import cu.vlired.esFacilCore.model.PagedData;
import cu.vlired.esFacilCore.model.User;
import cu.vlired.esFacilCore.payload.UserExistRequest;
import cu.vlired.esFacilCore.payload.SignInRequest;
import cu.vlired.esFacilCore.payload.UserStatusRequest;
import cu.vlired.esFacilCore.security.CurrentUser;
import cu.vlired.esFacilCore.security.UserData;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import java.util.List;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 *
 * @author luizo
 */
@Api( value = "Users", tags = {"User"})
public interface UserApi {
    
    @RequestMapping(value = "/user", method = RequestMethod.POST)
    ResponseEntity<User>                createUser(@ApiParam(value = "", required = true) @RequestBody User user);
    
    @RequestMapping(value = "/users", method = RequestMethod.GET)
    ResponseEntity<List<User>>          getAllUser();
    
    @RequestMapping(value = "/user/filter", method = RequestMethod.GET)
    ResponseEntity<PagedData<User>>     filterUsers(@ApiParam(value = "", required = true) @RequestParam Map<String, String> params);

    @RequestMapping(value = "/auth/sign-in", method = RequestMethod.POST)
    ResponseEntity<?>                   signIn(@RequestBody SignInRequest data);

    @RequestMapping(value = "/auth/sign-up", method = RequestMethod.POST)
    ResponseEntity<?>                   signUp(@RequestBody User user);

    /**
     * Get user object for a given JWT
     * @param statusRequest
     * @return
     */
    @RequestMapping(value = "/user/status", method = RequestMethod.POST)
    ResponseEntity<?>                   userStatus(@RequestBody UserStatusRequest statusRequest);

    @RequestMapping(value = "/user/{id}", method = RequestMethod.DELETE)
    ResponseEntity<?>                   deleteUser(@PathVariable long id);

    @RequestMapping(value = "/user", method = RequestMethod.PUT)
    ResponseEntity<?>                   updateUser(@RequestBody User user, @CurrentUser UserData currentUser);

    @RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
    ResponseEntity<?>                   getUserById(@PathVariable long id);

    @RequestMapping(value = "/user/exist-username", method = RequestMethod.POST)
    ResponseEntity<?>                   existUserByUsername(@RequestBody UserExistRequest request);

    @RequestMapping(value = "/user/exist-email", method = RequestMethod.POST)
    ResponseEntity<?>                   existUserByEmail(@RequestBody UserExistRequest request);

    @RequestMapping(value = "/user/search", method = RequestMethod.GET)
    ResponseEntity<?>                   search(@RequestParam(name = "pattern") String pattern);

    /**
     *  Fake states response for app sidebar
     * */
    @RequestMapping(value = "/user/states", method = RequestMethod.GET)
    ResponseEntity<?>                   getStates();

}

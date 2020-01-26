/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cu.vlired.esFacilCore.api;

import cu.vlired.esFacilCore.components.PaginationHelper;
import cu.vlired.esFacilCore.components.ResponsesHelper;
import cu.vlired.esFacilCore.exception.ResourceAlreadyTakenException;
import cu.vlired.esFacilCore.exception.ResourceNotFoundException;
import cu.vlired.esFacilCore.model.PagedData;
import cu.vlired.esFacilCore.model.User;
import cu.vlired.esFacilCore.payload.UserExistRequest;
import cu.vlired.esFacilCore.payload.SignInRequest;
import cu.vlired.esFacilCore.payload.UserStatusRequest;
import cu.vlired.esFacilCore.payload.UserStatusResponse;
import cu.vlired.esFacilCore.repository.UserRepository;

import java.util.*;

import cu.vlired.esFacilCore.security.CurrentUser;
import cu.vlired.esFacilCore.security.JwtTokenProvider;
import cu.vlired.esFacilCore.security.UserData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserApiController implements UserApi {

    @Autowired
    AuthenticationManager authenticationManager;
    
    @Autowired
    UserRepository userRepository;
    
    @Autowired
    ResponsesHelper responseHelper;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    JwtTokenProvider tokenProvider;

    @Autowired
    Random  randomGenerator;

    @Autowired
    PaginationHelper paginationHelper;

    @Override
    public ResponseEntity<User> createUser(@RequestBody User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        User saveUser = userRepository.save(user);
        return responseHelper.buildResponse(saveUser, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<User>> getAllUser() {
        List<User> findAll = userRepository.findAll();
        return responseHelper.buildResponse(findAll, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<PagedData<User>> filterUsers(@RequestParam Map<String, String> params) {

        // Create pageable using params
        Pageable pageable = paginationHelper.buildPageableByParams(params);

        long count      = userRepository.count();
        String search   = params.get("search");

        // Ugly dynamic method
        /** List<User> filterUsers = userRepository.findByUsernameContainingIgnoreCaseOrFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCaseOrEmailContainingIgnoreCase(
                search,
                search,
                search,
                search,
                pageable
        ); **/

        List<User> filterUsers = userRepository.paginateWithSearch(search, pageable);

        PagedData<User> pagedData = paginationHelper.buildResponseByParams(params, count, filterUsers);

        // Convert filtered users to UserPayload (Delete Password from response)
        filterUsers.stream().map(user -> UserStatusResponse.create(user));
        
        return responseHelper.buildResponse(pagedData, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> signIn(@RequestBody SignInRequest data) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        data.getUsername(),
                        data.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = tokenProvider.generateToken(authentication);
        Map<String, String> respose = new HashMap<>();
        respose.put("token", jwt);
        return responseHelper.ok(respose);
    }

    @Override
    public ResponseEntity<?> signUp(@RequestBody User user) {
        System.out.println(user);
        // Verify if user exist
        boolean userPresent = userRepository.findByUsername(user.getUsername()).isPresent();
        if ( userPresent ) throw new ResourceAlreadyTakenException("El usuario ya esta tomado");

        // Verify if email exist
        boolean emailPresent = userRepository.findByEmail(user.getEmail()).isPresent();
        if ( emailPresent ) throw new ResourceAlreadyTakenException("El correo ya esta tomado");

        // Creating user's account
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User result = userRepository.save(user);
        System.out.println(result);

        return responseHelper.ok(result);
    }

    @Override
    public ResponseEntity<?> userStatus(@RequestBody UserStatusRequest statusRequest) {
        // Get user id from token
        Long userId = tokenProvider.getUserIdFromJWT(statusRequest.getToken());

        Optional<User> user = userRepository.findById(userId);
        if ( !user.isPresent() ) throw new ResourceNotFoundException("Su usuario no existe");

        //Build response ( erase the password )
        UserStatusResponse response = UserStatusResponse.create(user.get());

        System.out.println("User for auth: \n" + user.get());
        // in the way ....
        return responseHelper.ok(response);
    }

    @Override
    public ResponseEntity<?> deleteUser(@PathVariable long id) {
        System.out.println("Deleting user "+id);

        Optional<User> user = userRepository.findById(id);
        if ( !user.isPresent() ) throw new ResourceNotFoundException("El usuario no existe");

        userRepository.deleteById(id);
        return responseHelper.ok(user);
    }

    @Override
    public ResponseEntity<?> updateUser(@RequestBody User user, @CurrentUser UserData currentUser) {
        Optional<User> oldUser = userRepository.findById(user.getId());
       
        if ( !oldUser.isPresent() ) throw new ResourceNotFoundException("El usuario no existe");

        // If given password NOT is null, the user want change it passwd
        if (user.getPassword() != null ) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        } else {
            user.setPassword(oldUser.get().getPassword());
        }

        // If logged in user not has ROLE_ADMIN
        // only can update mail, area y cargo.., and password upside
        if ( !currentUser.getAuthorities().contains( new SimpleGrantedAuthority("ROLE_ADMIN")) ){
            user.setEmail(user.getEmail());
         
        }

        userRepository.save(user);

        return responseHelper.ok(user);
    }

    @Override
    public ResponseEntity<?> getUserById(@PathVariable long id) {

        System.out.println("Getting user " + id);

        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("El usuario no existe"));
        return responseHelper.ok(UserStatusResponse.create(user));

    }

    @Override
    public ResponseEntity<?> existUserByUsername(@RequestBody UserExistRequest request) {

        String username = request.getUsername();
        UUID id = request.getId();
        System.out.println(id);

        System.out.println("Getting user with username "+username);
        Optional<User> user = userRepository.findByUsername(username);

        if ( user.isPresent() && user.get().getId() != id ) return responseHelper.ok(true);
        return responseHelper.ok(false);
    }

    @Override
    public ResponseEntity<?> existUserByEmail(@RequestBody UserExistRequest request) {

        String email = request.getEmail();
        UUID id = request.getId();

        System.out.println("Getting user with email "+email);
        Optional<User> user = userRepository.findByEmail(email);

        if ( user.isPresent() && user.get().getId() != id ) return responseHelper.ok(true);
        return responseHelper.ok(false);
    }

    @Override
    public ResponseEntity<?> search(@RequestParam(name = "pattern") String pattern) {

        System.out.println("Search term: "+pattern);
        List<User> filteredUsers = userRepository.findByUsernameContainingIgnoreCaseOrFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCaseOrEmailContainingIgnoreCase(
                pattern,
                pattern,
                pattern,
                pattern
        );

        return responseHelper.ok(filteredUsers);
    }

    @Override
    public ResponseEntity<?> getStates() {

        Map resp = new HashMap();
        resp.put("pendientes", randomGenerator.nextInt(100));
        resp.put("enEspera", randomGenerator.nextInt(100));
        resp.put("terminadas", randomGenerator.nextInt(100));
        resp.put("misPeticiones", randomGenerator.nextInt(100));

        return responseHelper.ok(resp);

    }

}


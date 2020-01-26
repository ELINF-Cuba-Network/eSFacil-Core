/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cu.vlired.esFacilCore.controller;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.*;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import cu.vlired.esFacilCore.api.*;
import cu.vlired.esFacilCore.components.*;
import cu.vlired.esFacilCore.exception.*;
import cu.vlired.esFacilCore.model.*;
import cu.vlired.esFacilCore.payload.*;
import cu.vlired.esFacilCore.repository.*;
import cu.vlired.esFacilCore.security.*;

@RestController
public class UserApiController implements UserApi {

    @Autowired
    UserRepository userRepository;
    
    @Autowired
    ResponsesHelper responseHelper;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

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
    public ResponseEntity<?> deleteUser(@PathVariable long id) {
        System.out.println("Deleting user "+id);

        Optional<User> user = userRepository.findById(id);
        if ( !user.isPresent() ) throw new ResourceNotFoundException("El usuario no existe");

        userRepository.deleteById(id);
        return responseHelper.ok(user);
    }

    @Override
    public ResponseEntity<?> updateUser(@RequestBody User user, @CurrentUser User currentUser) {
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


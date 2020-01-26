package cu.vlired.esFacilCore.controller;

import cu.vlired.esFacilCore.constants.Roles;
import cu.vlired.esFacilCore.payload.auth.LoginResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.*;

import cu.vlired.esFacilCore.api.*;
import cu.vlired.esFacilCore.components.*;
import cu.vlired.esFacilCore.exception.*;
import cu.vlired.esFacilCore.model.*;
import cu.vlired.esFacilCore.payload.*;
import cu.vlired.esFacilCore.repository.*;
import cu.vlired.esFacilCore.security.*;

@RestController
public class AuthApiController implements AuthApi {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtTokenProvider tokenProvider;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ResponsesHelper responseHelper;

    @Autowired
    PasswordEncoder passwordEncoder;

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

        LoginResponse response = new LoginResponse();
        response.setToken(jwt);

        return responseHelper.ok(response);
    }

    @Override
    public ResponseEntity<?> signUp(@RequestBody User user) {

        boolean userPresent = userRepository
                .findByUsername(user.getUsername())
                .isPresent();

        if ( userPresent ) {
            throw new ResourceAlreadyTakenException("El usuario ya esta tomado");
        }

        boolean emailPresent = userRepository
                .findByEmail(user.getEmail())
                .isPresent();

        if ( emailPresent ) {
            throw new ResourceAlreadyTakenException("El correo ya esta tomado");
        }

        user.setPassword(
            passwordEncoder.encode(user.getPassword())
        );

        // Users by default are ROLE_SUBMITTER
        user.setRoles(Arrays.asList(Roles.ROLE_SUBMITTER));

        User result = userRepository.save(user);
        return responseHelper.ok(result);
    }

    @Override
    public ResponseEntity<?> userStatus(@RequestBody UserStatusRequest statusRequest) {
        // Get user id from token
        UUID userId = tokenProvider.getUserIdFromJWT(statusRequest.getToken());

        Optional<User> user = userRepository.findById(userId);
        if ( !user.isPresent() ) throw new ResourceNotFoundException("Su usuario no existe");

        //Build response ( erase the password )
        UserStatusResponse response = UserStatusResponse.create(user.get());

        System.out.println("User for auth: \n" + user.get());
        // in the way ....
        return responseHelper.ok(response);
    }
}

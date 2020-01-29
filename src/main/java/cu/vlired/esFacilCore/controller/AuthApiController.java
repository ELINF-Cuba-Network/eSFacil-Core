package cu.vlired.esFacilCore.controller;

import cu.vlired.esFacilCore.constants.Roles;
import cu.vlired.esFacilCore.payload.auth.LoginResponse;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
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

    final
    AuthenticationManager authenticationManager;

    final
    JwtTokenProvider tokenProvider;

    final
    UserRepository userRepository;

    final
    ResponsesHelper responseHelper;

    final
    PasswordEncoder passwordEncoder;

    final
    MessageSource messageSource;

    public AuthApiController(
            AuthenticationManager authenticationManager,
            JwtTokenProvider tokenProvider,
            UserRepository userRepository,
            ResponsesHelper responseHelper,
            PasswordEncoder passwordEncoder,
            MessageSource messageSource
    ) {
        this.authenticationManager = authenticationManager;
        this.tokenProvider = tokenProvider;
        this.userRepository = userRepository;
        this.responseHelper = responseHelper;
        this.passwordEncoder = passwordEncoder;
        this.messageSource = messageSource;
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

        LoginResponse response = new LoginResponse();
        response.setToken(jwt);

        return responseHelper.ok(response);
    }

    @Override
    public ResponseEntity<?> signUp(@RequestBody User user) {

        System.out.println(user);
        boolean userPresent = userRepository
                .findByUsername(user.getUsername())
                .isPresent();

        if ( userPresent ) {
            throw new ResourceAlreadyTakenException(messageSource.getMessage("app.auth.username.already.taken", null, LocaleContextHolder.getLocale()));
        }

        boolean emailPresent = userRepository
                .findByEmail(user.getEmail())
                .isPresent();

        if ( emailPresent ) {
            throw new ResourceAlreadyTakenException(messageSource.getMessage("app.auth.email.already.taken", null, LocaleContextHolder.getLocale()));
        }

        user.setPassword(
            passwordEncoder.encode(user.getPassword())
        );

        // Users by default are ROLE_SUBMITTER
        user.setRoles(Collections.singletonList(Roles.ROLE_SUBMITTER));

        User result = userRepository.save(user);
        return responseHelper.ok(result);
    }
}

package cu.vlired.esFacilCore.controller;

import cu.vlired.esFacilCore.constants.Roles;
import cu.vlired.esFacilCore.model.dto.SignUpDTO;
import cu.vlired.esFacilCore.model.dto.UserDTO;
import cu.vlired.esFacilCore.payload.auth.LoginResponse;
import cu.vlired.esFacilCore.payload.auth.SignInRequest;
import cu.vlired.esFacilCore.services.DTOUtilService;
import lombok.extern.log4j.Log4j2;
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
import cu.vlired.esFacilCore.repository.*;
import cu.vlired.esFacilCore.security.*;

@Log4j2
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
    private DTOUtilService dtoUtilService;

    final
    I18n i18n;

    public AuthApiController(
            AuthenticationManager authenticationManager,
            JwtTokenProvider tokenProvider,
            UserRepository userRepository,
            ResponsesHelper responseHelper,
            PasswordEncoder passwordEncoder,
            DTOUtilService dtoUtilService,
            I18n i18n) {
        this.authenticationManager = authenticationManager;
        this.tokenProvider = tokenProvider;
        this.userRepository = userRepository;
        this.responseHelper = responseHelper;
        this.passwordEncoder = passwordEncoder;
        this.dtoUtilService = dtoUtilService;
        this.i18n = i18n;
    }

    @Override
    public ResponseEntity<?> signIn(@RequestBody SignInRequest data) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        data.getUsername(),
                        data.getPassword()
                )
        );

        log.debug(String.format("Sign in data %s", data));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = tokenProvider.generateToken(authentication);

        LoginResponse response = new LoginResponse();
        response.setToken(jwt);

        return responseHelper.ok(response);
    }

    @Override
    public ResponseEntity<?> signUp(@RequestBody SignUpDTO userDTO) throws Exception {

        User user = dtoUtilService.convertToEntity(userDTO, User.class);
        log.debug(String.format("Sign up user data %s", user));

        boolean userPresent = userRepository
                .findByUsername(user.getUsername())
                .isPresent();

        log.debug(String.format("username is present? %b", userPresent));

        if ( userPresent ) {
            throw new ResourceAlreadyTakenException(i18n.t("app.auth.username.already.taken"));
        }

        boolean emailPresent = userRepository
                .findByEmail(user.getEmail())
                .isPresent();

        log.debug(String.format("email is present? %b", emailPresent));

        if ( emailPresent ) {
            throw new ResourceAlreadyTakenException(i18n.t("app.auth.email.already.taken"));
        }

        user.setPassword(
            passwordEncoder.encode(user.getPassword())
        );

        // Users by default are ROLE_SUBMITTER
        user.setRoles(Collections.singletonList(Roles.ROLE_SUBMITTER));

        User result = userRepository.save(user);
        var res = dtoUtilService.convertToDTO(result, UserDTO.class);
        return responseHelper.ok(res);
    }
}

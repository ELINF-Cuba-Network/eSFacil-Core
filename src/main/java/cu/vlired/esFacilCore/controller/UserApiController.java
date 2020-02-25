package cu.vlired.esFacilCore.controller;

import java.util.*;

import cu.vlired.esFacilCore.model.dto.PatchUserDTO;
import cu.vlired.esFacilCore.model.dto.UserDTO;
import cu.vlired.esFacilCore.services.UserService;
import cu.vlired.esFacilCore.util.Page;
import cu.vlired.esFacilCore.util.PagedData;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.http.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import cu.vlired.esFacilCore.api.*;
import cu.vlired.esFacilCore.components.*;
import cu.vlired.esFacilCore.exception.*;
import cu.vlired.esFacilCore.model.*;
import cu.vlired.esFacilCore.payload.*;
import cu.vlired.esFacilCore.repository.*;
import cu.vlired.esFacilCore.security.*;

@Log4j2
@RestController
public class UserApiController implements UserApi {

    final
    UserRepository userRepository;

    final
    ResponsesHelper responseHelper;

    final
    PasswordEncoder passwordEncoder;

    private UserService userService;

    final
    PaginationHelper paginationHelper;

    final
    I18n i18n;

    public UserApiController(
            UserRepository userRepository,
            ResponsesHelper responseHelper,
            PasswordEncoder passwordEncoder,
            UserService userService,
            PaginationHelper paginationHelper,
            I18n i18n
    ) {
        this.userRepository = userRepository;
        this.responseHelper = responseHelper;
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
        this.paginationHelper = paginationHelper;
        this.i18n = i18n;
    }

    @Override
    public ResponseEntity<?> createUser(@RequestBody UserDTO user) {

        UserDTO createdUser = userService.create(user);
        return responseHelper.buildResponse(createdUser, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> updateUser(UUID id, @RequestBody UserDTO user) {

        if (!userRepository.existsById(id)) {
            throw new ResourceNotFoundException(
                    i18n.t("app.security.user.id.not.found", ArrayUtils.toArray(id))
            );
        }

        var updatedUser = userService.update(id, user);
        return responseHelper.ok(updatedUser);
    }

    @Override
    public ResponseEntity<?> patchUser(UUID id, @RequestBody PatchUserDTO user) {
        User old = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        i18n.t("app.security.user.id.not.found", ArrayUtils.toArray(id))
                ));

        var updatedUser = userService.patch(old, user);
        return responseHelper.ok(updatedUser);
    }

    @Override
    public ResponseEntity<?> getUserById(@PathVariable UUID id) {
        log.info(String.format("Getting user with id %s", id));

        UserDTO userDTO = userService.getById(id);
        return responseHelper.ok(userDTO);
    }

    @Override
    public ResponseEntity<?> listUsers(Page page) {
        log.debug(String.format("User request page %s", page));
        List<UserDTO> userDTO = userService.list(page);

        return responseHelper.ok(userDTO);
    }

    @Override
    public ResponseEntity<?> existUserByUsername(@RequestBody UserExistRequest request) {

        String username = request.getUsername();
        UUID id = request.getId();
        System.out.println(id);

        Optional<User> user = userRepository.findByUsername(username);

        if (user.isPresent() && user.get().getId() != id) return responseHelper.ok(true);
        return responseHelper.ok(false);
    }

    @Override
    public ResponseEntity<?> existUserByEmail(@RequestBody UserExistRequest request) {

        String email = request.getEmail();
        UUID id = request.getId();

        Optional<User> user = userRepository.findByEmail(email);

        if (user.isPresent() && user.get().getId() != id) return responseHelper.ok(true);
        return responseHelper.ok(false);
    }

    @Override
    public ResponseEntity<?> userStatus(@CurrentUser User currentUser) {
        UserDTO userDTO = userService.status(currentUser);
        return responseHelper.ok(userDTO);
    }

}


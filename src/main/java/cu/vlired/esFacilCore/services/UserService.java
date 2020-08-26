package cu.vlired.esFacilCore.services;

import cu.vlired.esFacilCore.components.I18n;
import cu.vlired.esFacilCore.constants.Roles;
import cu.vlired.esFacilCore.exception.ResourceNotFoundException;
import cu.vlired.esFacilCore.model.User;
import cu.vlired.esFacilCore.dto.PatchUserDTO;
import cu.vlired.esFacilCore.dto.UserDTO;
import cu.vlired.esFacilCore.repository.UserRepository;
import cu.vlired.esFacilCore.util.Page;
import org.apache.commons.lang3.ArrayUtils;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserService {

    final
    BCryptPasswordEncoder bCryptPasswordEncoder;

    private final DTOUtilService dtoUtilService;
    private final UserRepository userRepository;
    private final PaginateService paginateService;
    private final I18n i18n;

    public UserService(
            BCryptPasswordEncoder bCryptPasswordEncoder,
            DTOUtilService dtoUtilService,
            UserRepository userRepository,
            PaginateService paginateService,
            I18n i18n
    ) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.dtoUtilService = dtoUtilService;
        this.userRepository = userRepository;
        this.paginateService = paginateService;
        this.i18n = i18n;
    }

    public UserDTO create(UserDTO userDTO) {
        User newUser = dtoUtilService.convertToEntity(userDTO, User.class);
        newUser.setPassword(bCryptPasswordEncoder.encode(newUser.getPassword()));

        // Setting user role
        if (!newUser.getRoles().contains(Roles.ROLE_SUBMITTER)) {
            newUser.getRoles().add(Roles.ROLE_SUBMITTER);
        }

        User user = userRepository.save(newUser);
        return dtoUtilService.convertToDTO(user, UserDTO.class);
    }

    public UserDTO update(UUID id, UserDTO userDTO) {
        if (!userRepository.existsById(id)) {
            throw new ResourceNotFoundException(
                i18n.t("app.security.user.id.not.found", ArrayUtils.toArray(id))
            );
        }

        User user = dtoUtilService.convertToEntity(userDTO, User.class);
        user.setId(id);

        if (!user.getPassword().isBlank()) {
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        }

        // Setting user role
        if (!user.getRoles().contains(Roles.ROLE_SUBMITTER)) {
            user.getRoles().add(Roles.ROLE_SUBMITTER);
        }

        User newUser = userRepository.save(user);
        return dtoUtilService.convertToDTO(newUser, UserDTO.class);
    }

    public UserDTO patch(UUID id, PatchUserDTO patchUserDTO) {
        User user = userRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException(
                i18n.t("app.security.user.id.not.found", ArrayUtils.toArray(id))
            ));

        var modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setSkipNullEnabled(true);

        modelMapper.map(patchUserDTO, user);

        if (user.getPassword() != null && !user.getPassword().isBlank()) {
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        }

        // Setting user role
        if (!user.getRoles().contains(Roles.ROLE_SUBMITTER)) {
            user.getRoles().add(Roles.ROLE_SUBMITTER);
        }

        User newUser = userRepository.save(user);
        return dtoUtilService.convertToDTO(newUser, UserDTO.class);
    }

    public UserDTO getById(UUID id) {
        User user = userRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(i18n.t("app.security.user.id.not.found", ArrayUtils.toArray(id))));

        return dtoUtilService.convertToDTO(user, UserDTO.class);
    }

    public List<UserDTO> list(Page page) {
        paginateService.preProcess(page);
        List<User> list = userRepository.list(page);

        return list.stream()
                .map(user -> dtoUtilService.convertToDTO(user, UserDTO.class))
                .collect(Collectors.toList());
    }

    public User getCurrent() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();

        return (User) principal;
    }

    public UserDTO status(User user) {
        return dtoUtilService.convertToDTO(user, UserDTO.class);
    }
}
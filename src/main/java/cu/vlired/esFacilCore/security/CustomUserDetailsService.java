package cu.vlired.esFacilCore.security;

import cu.vlired.esFacilCore.components.I18n;
import cu.vlired.esFacilCore.model.User;
import cu.vlired.esFacilCore.repository.UserRepository;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collections;
import java.util.UUID;

@Log4j2
@Service
public class CustomUserDetailsService implements UserDetailsService {

    final
    UserRepository userRepository;

    final
    I18n i18n;

    public CustomUserDetailsService(UserRepository userRepository, I18n i18n) {
        this.userRepository = userRepository;
        this.i18n = i18n;
    }

    @Override
    public User loadUserByUsername(String email)
            throws AuthenticationServiceException {

        log.debug(String.format("Loading user %s", email));

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> {

                    log.debug(String.format("User %s not found", email));
                    return new AuthenticationServiceException(
                            i18n.t("app.security.user.email.not.found", ArrayUtils.toArray(email))
                    );
                });

        log.debug(String.format("User %s loaded", user));
        return user;
    }

    // This method is used by JWTAuthenticationFilter
    public User loadUserById(UUID id) {
        User user = userRepository.findById(id).orElseThrow(
                () -> new AuthenticationServiceException(
                        i18n.t("app.security.user.id.not.found", ArrayUtils.toArray(id))
                )
        );

        return user;
    }
}

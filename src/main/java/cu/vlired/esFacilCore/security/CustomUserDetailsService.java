package cu.vlired.esFacilCore.security;

import cu.vlired.esFacilCore.model.User;
import cu.vlired.esFacilCore.repository.UserRepository;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.UUID;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    final
    UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public User loadUserByUsername(String email)
            throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new UsernameNotFoundException("No existe el usuario : " + email)
                );

        return user;
    }

    // This method is used by JWTAuthenticationFilter
    @Transactional
    public UserDetails loadUserById(UUID id) {
        User user = userRepository.findById(id)
                .orElseThrow(() ->
                        new UsernameNotFoundException("No existe el usuario con id : " + id)
                );

        return user;
    }
}

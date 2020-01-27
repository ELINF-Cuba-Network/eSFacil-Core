package cu.vlired.esFacilCore;

import cu.vlired.esFacilCore.constants.Roles;
import cu.vlired.esFacilCore.model.User;
import cu.vlired.esFacilCore.repository.UserRepository;

import java.util.Arrays;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
@EnableJpaRepositories("cu.vlired.esFacilCore.repository")
public class SubmodApplication {

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    public static void main(String[] args) {
        SpringApplication.run(SubmodApplication.class, args);
    }

    @Bean
    public CommandLineRunner demo(UserRepository repository) {
        return (args) -> {

            // Create admin if not exist
            Optional<User> admin = repository.findByUsername("admin");

            if (!admin.isPresent()) {
                User u = new User();
                u.setUsername("admin");
                u.setFirstName("Administrator");
                u.setRoles(Arrays.asList(Roles.ROLE_ADMIN));
                u.setActive(true);
                u.setEmail("admin@vlired.cu");
                u.setPassword(bCryptPasswordEncoder.encode("admin123"));
                repository.save(u);
            }
        };
    }

}

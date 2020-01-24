package cu.vlired.esFacilCore;

import cu.vlired.esFacilCore.model.Role;
import cu.vlired.esFacilCore.model.User;
import cu.vlired.esFacilCore.repository.RoleRepository;
import cu.vlired.esFacilCore.repository.UserRepository;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

    // ROLES FROM PROPS
    @Value("#{'${app.roles}'.split(',')}")
    private List<String> roles;

    public static void main(String[] args) {
        SpringApplication.run(SubmodApplication.class, args);
    }

    @Bean
    public CommandLineRunner demo(UserRepository repository, RoleRepository roleRepository) {
        return (args) -> {

            roles.forEach(role -> {

                Optional<Role> roleOptional = roleRepository.findByName(role);
                if (!roleOptional.isPresent()) {
                    Role r = new Role();
                    r.setName(role);
                    roleRepository.save(r);
                }
            });

            // Create admin if not exist
            Optional<User> admin = repository.findByUsername("admin");

            if (!admin.isPresent()) {
                User u = new User();
                u.setUsername("admin");
                u.setFirstName("Administrator");
                u.setRole(roleRepository.findByName("ROLE_ADMIN").get());
                u.setActive(true);
                u.setEmail("admin@vlired.cu");
                u.setPassword(bCryptPasswordEncoder.encode("admin123"));
                repository.save(u);
            }
        };
    }

}

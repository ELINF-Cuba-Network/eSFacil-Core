package cu.vlired.esFacilCore.components;

import cu.vlired.esFacilCore.constants.Roles;
import cu.vlired.esFacilCore.model.User;
import cu.vlired.esFacilCore.repository.UserRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.Collections;
import java.util.Optional;

@Log4j2
@Component
public class Startup implements CommandLineRunner {

    final
    BCryptPasswordEncoder bCryptPasswordEncoder;

    final
    UserRepository userRepository;

    @Value("${dir.assetstore}")
    private String assetstore;

    public Startup(BCryptPasswordEncoder bCryptPasswordEncoder, UserRepository userRepository) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userRepository = userRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        createFirstAdmin();
        createDirectories();
    }

    private void createDirectories() {
        File directory = new File(assetstore);

        log.info(String.format("Assetstore directory exist? %b", directory.exists()));

        if (!directory.exists()) {
            boolean mkdir = directory.mkdir();
            log.info(String.format("Assetstore directory created? %b", mkdir));
        }
    }

    private void createFirstAdmin() {
        Optional<User> admin = userRepository.findByUsername("admin");
        log.info(String.format("Admin exist? %b", admin.isPresent()));

        if (admin.isEmpty()) {
            User u = new User();
            u.setUsername("admin");
            u.setFirstname("Administrator");
            u.setRoles(Collections.singletonList(Roles.ROLE_ADMIN));
            u.setActive(true);
            u.setEmail("admin@vlired.cu");
            u.setPassword(bCryptPasswordEncoder.encode("admin123"));
            userRepository.save(u);
        }
    }
}

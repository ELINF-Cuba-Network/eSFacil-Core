package cu.vlired.submod.config;

import cu.vlired.submod.security.UserData;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.client.RestTemplate;

import java.util.Random;

@Configuration
public class Beans {

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public Random   randomGenerator() {
        return new Random();
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}


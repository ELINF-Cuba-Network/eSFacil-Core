package cu.vlired.esFacilCore.config;

import org.hobsoft.spring.resttemplatelogger.LoggingCustomizer;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.client.RestTemplate;
import java.net.http.HttpClient;
import java.time.Duration;
import java.util.Random;

@Configuration
public class Beans {

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public Random randomGenerator() {
        return new Random();
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplateBuilder()
            .customizers(new LoggingCustomizer())
            .build();
    }

    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource
                = new ReloadableResourceBundleMessageSource();

        messageSource.setBasename("classpath:messages");
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }

    @Bean
    public LocalValidatorFactoryBean getValidator() {
        LocalValidatorFactoryBean bean = new LocalValidatorFactoryBean();
        bean.setValidationMessageSource(messageSource());
        return bean;
    }

    @Bean
    public HttpClient httpClient() {
        return HttpClient.newBuilder()
            .connectTimeout(Duration.ofSeconds(20))
            .build();
    }

}


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cu.vlired.submod.config;

import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author luizo
 */

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("cu.vlired.submod"))
                .build()
                .apiInfo(
                    new ApiInfo(
                        "EsFacil",
                        "Deposit Module for DSpace",
                        "0.1",
                        "",
                        new Contact("Jose Javier Hdez", "vlired.cu", "jjbenitez@uci.cu"),
                        "licenseName",
                        "licenseWebsite",
                        Collections.emptyList()
                    )
                );
    }


}

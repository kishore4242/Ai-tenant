package com.aitenant.web_service.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("web-service API Operations")
                        .version("3.0.3")
                        .description("Production-ready endpoints for managing store items.")
                        .contact(new Contact().name("Dev Team").email("kishore4242421@gmail.com")));
    }
}

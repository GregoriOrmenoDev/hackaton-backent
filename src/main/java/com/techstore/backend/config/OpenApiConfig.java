package com.techstore.backend.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI techStoreOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("TechStore Backend API")
                        .description("API REST - Hackaton Template")
                        .version("1.0.0"));
    }
}

package com.ayaco.spotifou.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI spotifouOpenAPI() {
        return new OpenAPI()
            .info(new Info()
                .title("Spotifou API")
                .description("Backend API for Spotifou music streaming application")
                .version("1.0.0"))
            .servers(List.of(
                new Server().url("http://localhost:8080").description("Local development")
            ));
    }
}

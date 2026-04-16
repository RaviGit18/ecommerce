package com.ravi.ecommerce.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * Swagger/OpenAPI configuration for the E-commerce API.
 * This class configures the API documentation using SpringDoc OpenAPI.
 */
@Configuration
public class SwaggerConfig {

    /**
     * Configures the OpenAPI specification for the API.
     * 
     * @return OpenAPI object with API metadata
     */
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("E-commerce API")
                        .description("A comprehensive REST API for e-commerce inventory management")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Ravi Kumar")
                                .email("ravi.kumar@example.com")
                                .url("https://github.com/RaviGit18/ecommerce"))
                        .license(new License()
                                .name("MIT License")
                                .url("https://opensource.org/licenses/MIT")))
                .servers(List.of(
                        new Server()
                                .url("http://localhost:8080")
                                .description("Development Server"),
                        new Server()
                                .url("https://api.ecommerce.example.com")
                                .description("Production Server")
                ));
    }
}

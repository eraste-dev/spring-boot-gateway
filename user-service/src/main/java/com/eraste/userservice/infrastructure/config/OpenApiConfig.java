package com.eraste.userservice.infrastructure.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * Configuration class for OpenAPI/Swagger documentation.
 * <p>
 * This class customizes the OpenAPI specification for the User Service API,
 * including API metadata, contact information, license, and server URLs.
 * </p>
 * <p>
 * The Swagger UI is available at: /swagger-ui.html<br>
 * The OpenAPI JSON spec is available at: /api-docs
 * </p>
 *
 * @author Eraste
 * @version 1.0.0
 * @since 1.0.0
 */
@Configuration
public class OpenApiConfig {

    /** Server port injected from application configuration. */
    @Value("${server.port:8081}")
    private String serverPort;

    /**
     * Creates and configures the OpenAPI specification bean.
     * <p>
     * Configures:
     * <ul>
     *   <li>API title, version, and description</li>
     *   <li>Contact information</li>
     *   <li>License (Apache 2.0)</li>
     *   <li>Server URLs for different environments</li>
     * </ul>
     * </p>
     *
     * @return the configured OpenAPI specification
     */
    @Bean
    public OpenAPI userServiceOpenAPI() {
        Server devServer = new Server();
        devServer.setUrl("http://localhost:" + serverPort);
        devServer.setDescription("Development server");

        Contact contact = new Contact();
        contact.setName("Eraste");
        contact.setEmail("contact@eraste.com");

        License license = new License()
                .name("Apache 2.0")
                .url("https://www.apache.org/licenses/LICENSE-2.0");

        Info info = new Info()
                .title("User Service API")
                .version("1.0.0")
                .description("REST API for user management in the microservices architecture")
                .contact(contact)
                .license(license);

        return new OpenAPI()
                .info(info)
                .servers(List.of(devServer));
    }
}

package org.example.cloud2fa.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * OpenAPI/Swagger configuration for Cloud 2FA API documentation.
 * 
 * Access Swagger UI at: http://localhost:{port}/swagger-ui.html
 * Access OpenAPI spec at: http://localhost:{port}/v3/api-docs
 */
@Configuration
public class OpenApiConfig {

   @Value("${server.port:8080}")
   private String serverPort;

   @Bean
   public OpenAPI customOpenAPI() {
      final String securitySchemeName = "bearerAuth";

      return new OpenAPI()
            .info(new Info()
                  .title("Cloud 2FA API")
                  .version("1.0.0")
                  .description("""
                        Cloud 2FA is a secure cloud-based Two-Factor Authentication service.

                        ## Features
                        - User authentication (register, login)
                        - TOTP account management (add, update, delete)
                        - TOTP code generation
                        - Offline data sync with encrypted secrets

                        ## Authentication
                        Most endpoints require Bearer token authentication.
                        Obtain a token via the `/api/v1/auth/login` endpoint.
                        """)
                  .contact(new Contact()
                        .name("Cloud 2FA Team")
                        .email("support@cloud2fa.example.com"))
                  .license(new License()
                        .name("MIT License")
                        .url("https://opensource.org/licenses/MIT")))
            .servers(List.of(
                  new Server()
                        .url("http://localhost:" + serverPort)
                        .description("Development Server")))
            .addSecurityItem(new SecurityRequirement().addList(securitySchemeName))
            .components(new Components()
                  .addSecuritySchemes(securitySchemeName,
                        new SecurityScheme()
                              .name(securitySchemeName)
                              .type(SecurityScheme.Type.HTTP)
                              .scheme("bearer")
                              .bearerFormat("JWT")
                              .description("Enter JWT token obtained from login endpoint")));
   }
}

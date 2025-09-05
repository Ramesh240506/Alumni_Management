package com.example.alumni.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
    info = @Info(
        title = "Alumni Management & Career Bridge API",
        version = "1.0",
        description = "API documentation for the Alumni Management System"
    ),
    security = @SecurityRequirement(name = "bearerAuth") // Applies security to all endpoints
)
@SecurityScheme(
    name = "bearerAuth",
    type = SecuritySchemeType.HTTP,
    scheme = "bearer",
    bearerFormat = "JWT"
)
public class SwaggerConfig {
    // This configuration enables a UI to view and test your API endpoints.
    // To use it:
    // 1. Add the springdoc-openapi dependency to your pom.xml:
    //    <dependency>
    //        <groupId>org.springdoc</groupId>
    //        <artifactId>springdoc-openapi-starter-webmvc-ui</artifactId>
    //        <version>2.5.0</version>
    //    </dependency>
    // 2. Run your application.
    // 3. Go to http://localhost:8080/alumni-api/swagger-ui.html in your browser.
    // 4. You can authorize by clicking the "Authorize" button and pasting your JWT
    //    in the format: Bearer <your_token>
}
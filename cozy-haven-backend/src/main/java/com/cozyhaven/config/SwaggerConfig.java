package com.cozyhaven.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    private static final String SCHEME_NAME = "bearerAuth";
    private static final String SCHEME = "Bearer";

    @Bean
    public OpenAPI cozyHavenOpenAPI() {

        /* ----- Security scheme ----- */
        SecurityScheme jwtScheme = new SecurityScheme()
                .name(SCHEME_NAME)
                .description("Paste **only** the JWT token, without the `Bearer` prefix")
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT");

        /* ----- Apply scheme globally ----- */
        SecurityRequirement requirement = new SecurityRequirement().addList(SCHEME_NAME);

        return new OpenAPI()
                .info(new Info()
                        .title("Cozy Haven Stay API")
                        .version("1.0")
                        .description("Backend API for Cozy Haven Stay"))
                .addSecurityItem(requirement)
                .components(new Components().addSecuritySchemes(SCHEME_NAME, jwtScheme));
    }
}

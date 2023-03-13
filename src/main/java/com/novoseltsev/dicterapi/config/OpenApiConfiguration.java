package com.novoseltsev.dicterapi.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import java.util.Collections;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfiguration {

    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String AUTHORIZATION_NAME = "JWT Authorization";

    @Bean
    public OpenAPI openAPI(@Value("${server.base.url}") String serverBaseUrl,
                           @Value("${server.servlet.context-path}") String serverContextPath) {
        return new OpenAPI()
            .info(new Info()
                .title("Dicter API")
                .description("API to work with terms to remind them")
                .version("1.0")
            )
            .servers(Collections.singletonList(new Server().url(serverBaseUrl + serverContextPath)))
            .addSecurityItem(new SecurityRequirement().addList(AUTHORIZATION_NAME))
            .components(new Components()
                .addSecuritySchemes(AUTHORIZATION_NAME, new SecurityScheme()
                    .name(AUTHORIZATION_HEADER)
                    .type(SecurityScheme.Type.HTTP)
                    .scheme("bearer")
                    .description("Provide the JWT. JWT can be obtained from the \"authentication\" API. "
                        + "For testing, you may use default credentials: <strong>admin/Admin12</strong>")
                    .bearerFormat("JWT")
                    .in(SecurityScheme.In.HEADER)));
    }
}

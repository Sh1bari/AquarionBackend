package com.example.aquarionBackend.configs.openApi;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

/**
 * Open Api Configuration
 * @author Vladimir Krasnov
 */
@OpenAPIDefinition(
        info = @Info(
                description = "Backend"
        ),
        servers = {
                //@Server(url = "https://vr.cloudioti.com/api"),
                @Server(url = "http://localhost:8001/api")
        }
)
@Configuration
public class OpenApiConfig {
}

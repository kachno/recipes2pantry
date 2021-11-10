package pl.recipes2pantry.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
class SwaggerUIConfiguration {
    @Value("${app-info.url}")
    private String serverUrl;

    @Bean
    public OpenAPI customOpenAPI() {
        var info = new Info()
                .title("Recipes2Pantry API")
                .description("This is a Recipes2Pantry documentation using Swagger UI");

        var server = new Server()
                .url(serverUrl);

        return new OpenAPI()
                .servers(List.of(server))
                .info(info)
                .components(new Components()
                        .addSecuritySchemes("basic", new SecurityScheme()
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("basic")
                        )
                        .addSecuritySchemes("api-key", new SecurityScheme()
                                .type(SecurityScheme.Type.APIKEY)
                                .in(SecurityScheme.In.HEADER)
                                .name("api-key")
                        )
                );
    }
}

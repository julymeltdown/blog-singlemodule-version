package com.julymeltdown.blog.infrastructure.config

import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.security.SecurityScheme
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class SwaggerConfiguration {
    @Bean
    fun customOpenAPI(): OpenAPI {
        return OpenAPI()
            .components(
                Components()
                    .addSecuritySchemes(
                        "Authorization",
                        SecurityScheme()
                            .type(SecurityScheme.Type.HTTP)
                            .scheme("Bearer")
                            .bearerFormat("JWT")
                    )
            )
            .info(
                Info()
                    .title("Blog API")
                    .version("1.0.0")
            )
    }
}
package com.julymeltdown.blog.infrastructure.config

import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.security.SecurityRequirement
import io.swagger.v3.oas.models.security.SecurityScheme
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class SwaggerConfiguration {
    @Bean
    fun customOpenAPI(): OpenAPI {
        val securityScheme = SecurityScheme()
            .type(SecurityScheme.Type.HTTP)
            .scheme("bearer")
            .bearerFormat("JWT")
            .`in`(SecurityScheme.In.HEADER)
            .name("Authorization")
        val info = Info()
            .title("Yourssu Blog")
            .description("Blog API")
            .version("v1.0.0")
        return OpenAPI()
            .components(Components().addSecuritySchemes("Authorization", securityScheme))
            .addSecurityItem(
                SecurityRequirement().addList("Authorization")
            )
            .info(info)
    }
}
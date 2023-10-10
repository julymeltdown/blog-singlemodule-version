package com.julymeltdown.blog.infrastructure.config

import com.fasterxml.jackson.databind.ObjectMapper
import com.julymeltdown.blog.infrastructure.security.JwtAuthenticationFilter
import com.julymeltdown.blog.infrastructure.security.JwtTokenProvider
import javax.servlet.http.HttpServletResponse
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.access.AccessDeniedHandler
import org.springframework.security.web.util.matcher.AntPathRequestMatcher

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
class SecurityConfig(
    private val objectMapper: ObjectMapper,
    private val jwtTokenProvider: JwtTokenProvider
) {

    @Bean
    @Throws(Exception::class)
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .httpBasic { it.disable() }
            .csrf {
                it.disable()
                it.ignoringAntMatchers("/h2-console/**")
            }.cors { }
            .sessionManagement { it.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }.authorizeHttpRequests {
                it.requestMatchers(
                    AntPathRequestMatcher("/**")
                ).permitAll()
            }
            .headers {
                it.frameOptions().disable()
            }
            .authorizeHttpRequests{
                it.requestMatchers(
                    AntPathRequestMatcher("/api/users", HttpMethod.POST.name)
                ).permitAll()
            }
        return http.build()
    }

    @Bean
    fun jwtAuthenticationFilter(): JwtAuthenticationFilter {
        return JwtAuthenticationFilter(jwtTokenProvider, objectMapper)
    }
    @Bean
    fun passwordEncoder(): BCryptPasswordEncoder {
        return BCryptPasswordEncoder()
    }
}

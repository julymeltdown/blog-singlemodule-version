package com.julymeltdown.blog.infrastructure

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@SpringBootApplication(scanBasePackages = ["com.julymeltdown.blog"])
@EnableJpaRepositories(basePackages = ["com.julymeltdown.blog"])
@EntityScan(basePackages = ["com.julymeltdown.blog"])
class InfrastructureApplication

fun main(args: Array<String>) {
    runApplication<InfrastructureApplication>(*args)
}

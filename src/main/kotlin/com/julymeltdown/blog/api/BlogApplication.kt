package com.julymeltdown.blog.api

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaAuditing

@SpringBootApplication(scanBasePackages = ["com.julymeltdown.blog"])
@EntityScan(basePackages = ["com.julymeltdown.blog"])
@EnableJpaAuditing
class BlogApplication

fun main(args: Array<String>) {
    runApplication<BlogApplication>(*args)
}

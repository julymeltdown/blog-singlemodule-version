package com.julymeltdown.blog.domain

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.boot.runApplication

@SpringBootApplication(scanBasePackages = ["com.julymeltdown.blog"])
@EntityScan(basePackages = ["com.julymeltdown.blog"])
class DomainApplication

fun main(args: Array<String>) {
    runApplication<DomainApplication>(*args)
}

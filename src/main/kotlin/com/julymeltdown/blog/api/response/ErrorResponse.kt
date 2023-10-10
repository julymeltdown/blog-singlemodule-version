package com.julymeltdown.blog.api.response

import org.springframework.http.HttpStatus
import java.time.LocalDateTime

data class ErrorResponse(
    val time: LocalDateTime,
    val status: String,
    val message: String,
    val requestURI: String
) {
    constructor(status: HttpStatus, message: String, requestURI: String) : this(
        LocalDateTime.now(),
        status.toString(),
        message,
        requestURI
    )
}
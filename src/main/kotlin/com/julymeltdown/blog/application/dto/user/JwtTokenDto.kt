package com.julymeltdown.blog.application.dto.user

data class JwtTokenDto(
    val token: String,
    val expiredIn: Long
)
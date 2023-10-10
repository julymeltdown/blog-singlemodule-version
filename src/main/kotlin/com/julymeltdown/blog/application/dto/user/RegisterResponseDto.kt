package com.julymeltdown.blog.application.dto.user

data class RegisterResponseDto(
    val email: String,
    val username: String,
    val role: String
)
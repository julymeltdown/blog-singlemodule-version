package com.julymeltdown.blog.application.dto.user

data class RegisterRequestDto(
    val email: String,
    val password: String,
    val username: String
)
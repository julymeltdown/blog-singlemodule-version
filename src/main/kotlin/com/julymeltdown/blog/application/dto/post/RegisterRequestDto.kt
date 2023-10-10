package com.julymeltdown.blog.application.dto.post

data class RegisterRequestDto(
    val email: String,
    val password: String,
    val username: String
)
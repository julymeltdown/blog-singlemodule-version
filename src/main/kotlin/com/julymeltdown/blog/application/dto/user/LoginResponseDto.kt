package com.julymeltdown.blog.application.dto.user

data class LoginResponseDto(
    val accessToken: String,
    val refreshToken: String
)
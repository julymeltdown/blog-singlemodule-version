package com.julymeltdown.blog.api.response

data class LoginResponse(
    val accessToken: String,
    val refreshToken: String
)
package com.julymeltdown.blog.api.request

data class DeleteUserRequest(
    val email: String,
    val password: String
)
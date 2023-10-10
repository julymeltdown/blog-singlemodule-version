package com.julymeltdown.blog.api.request

data class CommentRequest(
    val email: String,
    val password: String,
    val content: String
)
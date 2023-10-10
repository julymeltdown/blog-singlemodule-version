package com.julymeltdown.blog.api.request

data class DeleteCommentRequest(
    val email: String,
    val password: String
)
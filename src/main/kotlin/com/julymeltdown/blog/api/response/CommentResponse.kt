package com.julymeltdown.blog.api.response

data class CommentResponse(
    val commentId: Long,
    val email: String,
    val content: String
)
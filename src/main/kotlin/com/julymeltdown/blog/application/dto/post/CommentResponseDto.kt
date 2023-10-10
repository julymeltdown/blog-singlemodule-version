package com.julymeltdown.blog.application.dto.post

data class CommentResponseDto(
    val commentId: Long,
    val email: String,
    val content: String
)
package com.julymeltdown.blog.application.dto.post

data class CommentRequestDto(
    val articleId: Long,
    val email: String,
    val password: String,
    val content: String
)
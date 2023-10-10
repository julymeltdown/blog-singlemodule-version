package com.julymeltdown.blog.application.dto.post

data class DeleteCommentDto(
    val email: String,
    val password: String,
    val articleId: Long,
    val commentId: Long
)
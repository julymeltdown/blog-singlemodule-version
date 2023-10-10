package com.julymeltdown.blog.application.dto.post

data class DeleteCommentDto(
    val email: String,
    val articleId: Long,
    val commentId: Long
)
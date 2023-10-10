package com.julymeltdown.blog.application.dto.post

data class DeleteArticleDto(
    val email: String,
    val password: String,
    val articleId: Long
)
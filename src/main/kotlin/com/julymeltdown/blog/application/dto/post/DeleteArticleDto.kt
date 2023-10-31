package com.julymeltdown.blog.application.dto.post

data class DeleteArticleDto(
    val email: String,
    val articleId: Long
)
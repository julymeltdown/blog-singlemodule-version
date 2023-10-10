package com.julymeltdown.blog.application.dto.post

data class ArticleResponseDto(
    val articleId: Long,
    val email: String,
    val title: String,
    val content: String
)
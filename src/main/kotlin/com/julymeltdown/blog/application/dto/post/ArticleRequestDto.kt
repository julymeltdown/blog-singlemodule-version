package com.julymeltdown.blog.application.dto.post

data class ArticleRequestDto(
    val email: String,
    val password: String,
    val title: String,
    val content: String
)
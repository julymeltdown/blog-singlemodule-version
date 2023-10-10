package com.julymeltdown.blog.api.request

data class ArticleRequest(
    val email: String,
    val password: String,
    val title: String,
    val content: String
)
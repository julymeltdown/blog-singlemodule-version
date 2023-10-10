package com.julymeltdown.blog.api.response

data class ArticleResponse(
    val articleId: Long,
    val email: String,
    val title: String,
    val content: String
)
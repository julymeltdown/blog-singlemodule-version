package com.julymeltdown.blog.domain.model.posting.repository

import com.julymeltdown.blog.domain.model.posting.entity.Article
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface CustomArticleRepository {
    fun findArticlesByUserUserId(userId: Long, pageable: Pageable): Page<Article>
    fun findArticleByArticleId(articleId: Long): Article?
}

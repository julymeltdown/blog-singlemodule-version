package com.julymeltdown.blog.domain.model.posting.repository

import com.julymeltdown.blog.domain.model.posting.entity.Comment
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface CustomCommentRepository {
    fun findCommentsByArticleArticleId(articleId: Long, pageable: Pageable): Page<Comment>
    fun findCommentByCommentId(commentId: Long): Comment?
}
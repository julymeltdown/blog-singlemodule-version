package com.julymeltdown.blog.domain.model.posting.repository

import com.julymeltdown.blog.domain.model.posting.entity.User

interface CustomUserRepository {
    fun findByEmail(email: String): User?
    fun findWithArticlesByUserId(userId: Long): User?
    fun findWithCommentsByUserId(userId: Long): User?
    fun existsByEmail(email: String): Boolean

    fun findAdminUser(): User?
}

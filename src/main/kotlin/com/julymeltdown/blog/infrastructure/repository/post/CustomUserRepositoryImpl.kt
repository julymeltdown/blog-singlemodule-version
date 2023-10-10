package com.julymeltdown.blog.infrastructure.repository.post

import com.julymeltdown.blog.domain.model.posting.entity.QArticle
import com.julymeltdown.blog.domain.model.posting.entity.QComment
import com.julymeltdown.blog.domain.model.posting.entity.QUser
import com.julymeltdown.blog.domain.model.posting.entity.User
import com.julymeltdown.blog.domain.model.posting.repository.CustomUserRepository
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Repository

@Repository
class CustomUserRepositoryImpl(
    private val queryFactory: JPAQueryFactory,
) : CustomUserRepository {
    val qUser = QUser.user
    val qArticle = QArticle.article
    val qComment = QComment.comment

    override fun findByEmail(email: String): User? {
        return queryFactory.selectFrom(qUser)
            .where(qUser.email.eq(email))
            .fetchOne()
    }

    override fun findWithArticlesByUserId(userId: Long): User? {
        return queryFactory.selectFrom(qUser)
            .leftJoin(qUser.articles, qArticle).fetchJoin()
            .where(qUser.userId.eq(userId))
            .fetchOne()
    }

    override fun findWithCommentsByUserId(userId: Long): User? {
        return queryFactory.selectFrom(qUser)
            .leftJoin(qUser.comments, qComment).fetchJoin()
            .where(qUser.userId.eq(userId))
            .fetchOne()
    }

    override fun existsByEmail(email: String): Boolean {
        return queryFactory.selectFrom(qUser)
            .where(qUser.email.eq(email))
            .fetchCount() > 0
    }
}

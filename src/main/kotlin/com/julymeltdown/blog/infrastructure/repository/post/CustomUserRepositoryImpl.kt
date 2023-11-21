package com.julymeltdown.blog.infrastructure.repository.post

import com.julymeltdown.blog.domain.model.posting.entity.QArticle
import com.julymeltdown.blog.domain.model.posting.entity.QComment
import com.julymeltdown.blog.domain.model.posting.entity.QUser
import com.julymeltdown.blog.domain.model.posting.entity.User
import com.julymeltdown.blog.domain.model.posting.enums.Role
import com.julymeltdown.blog.domain.model.posting.repository.CustomUserRepository
import com.querydsl.core.types.dsl.Expressions
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
            .fetchOne() != null
    }

    override fun findAdminUser(
        username: String,
        email: String,
        createdAtStart: String,
        createdAtEnd: String,
        updatedAtStart: String,
        updatedAtEnd: String
    ): User? {

        val createdAtStartDateTime =
            Expressions.stringTemplate("FUNCTION('DATE_FORMAT', {0}, '%Y-%m-%d')", qUser.createdAt)
        val createdAtEndDateTime =
            Expressions.stringTemplate("FUNCTION('DATE_FORMAT', {0}, '%Y-%m-%d')", qUser.createdAt)
        val updatedAtStartDateTime =
            Expressions.stringTemplate("FUNCTION('DATE_FORMAT', {0}, '%Y-%m-%d')", qUser.modifiedAt)
        val updatedAtEndDateTime =
            Expressions.stringTemplate("FUNCTION('DATE_FORMAT', {0}, '%Y-%m-%d')", qUser.modifiedAt)

        return queryFactory.selectFrom(qUser)
            .where(qUser.role.eq(Role.USER))
            .where(qUser.username.eq(username))
            .where(qUser.email.eq(email))
            .where(createdAtStartDateTime.goe(createdAtStart))
            .where(createdAtEndDateTime.loe(createdAtEnd))
            .where(updatedAtStartDateTime.goe(updatedAtStart))
            .where(updatedAtEndDateTime.loe(updatedAtEnd))
            .fetchOne()
    }
}

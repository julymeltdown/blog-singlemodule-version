package com.julymeltdown.blog.infrastructure.repository.post

import com.julymeltdown.blog.domain.model.posting.entity.Comment
import com.julymeltdown.blog.domain.model.posting.entity.QComment
import com.julymeltdown.blog.domain.model.posting.repository.CustomCommentRepository
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Repository

@Repository
class CustomCommentRepositoryImpl(
    private val queryFactory: JPAQueryFactory
) : CustomCommentRepository {
    override fun findCommentsByArticleArticleId(articleId: Long, pageable: Pageable): Page<Comment> {
        val query = queryFactory.selectFrom(QComment.comment)
            .where(QComment.comment.article.articleId.eq(articleId))
            .orderBy(QComment.comment.createdAt.desc())
            .offset(pageable.offset)
            .limit(pageable.pageSize.toLong())
        val results = query.fetchResults()
        return PageImpl(
            results.results,
            pageable,
            results.total
        )
    }

    override fun findCommentByCommentId(commentId: Long): Comment? {
        return queryFactory.selectFrom(QComment.comment)
            .where(QComment.comment.commentId.eq(commentId))
            .fetchOne()
    }
}
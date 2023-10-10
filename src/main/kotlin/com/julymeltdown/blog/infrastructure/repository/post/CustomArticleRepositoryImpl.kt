package com.julymeltdown.blog.infrastructure.repository.post

import com.julymeltdown.blog.domain.model.posting.entity.Article
import com.julymeltdown.blog.domain.model.posting.entity.QArticle
import com.julymeltdown.blog.domain.model.posting.repository.CustomArticleRepository
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Repository

@Repository
class CustomArticleRepositoryImpl(
    private val queryFactory: JPAQueryFactory
) : CustomArticleRepository {

    override fun findArticlesByUserUserId(userId: Long, pageable: Pageable): Page<Article> {
        val query = queryFactory.selectFrom(QArticle.article)
            .where(QArticle.article.user.userId.eq(userId))
            .offset(pageable.offset)
            .limit(pageable.pageSize.toLong())

        val results = query.fetch()
        val total = queryFactory.selectFrom(QArticle.article)
            .where(QArticle.article.user.userId.eq(userId))
            .fetchCount()

        return PageImpl(
            results,
            pageable,
            total
        )
    }

    override fun findArticleByArticleId(articleId: Long): Article? {
        val query = queryFactory.selectFrom(QArticle.article)
            .where(QArticle.article.articleId.eq(articleId))
        return query.fetchOne()
    }
}

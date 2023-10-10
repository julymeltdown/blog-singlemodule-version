package com.julymeltdown.blog.application.service.post

import com.julymeltdown.blog.application.dto.post.ArticleResponseDto
import com.julymeltdown.blog.application.dto.post.ArticleRequestDto
import com.julymeltdown.blog.application.dto.post.DeleteArticleDto
import com.julymeltdown.blog.domain.exceptions.ArticleNotFoundException
import com.julymeltdown.blog.domain.exceptions.NotArticleOwnerException
import com.julymeltdown.blog.domain.model.posting.entity.Article
import com.julymeltdown.blog.infrastructure.repository.post.ArticleRepository
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ArticleService(
    private val articleRepository: ArticleRepository,
    private val userService: UserService,
) {
    @Transactional
    fun createArticle(requestDto: ArticleRequestDto): ArticleResponseDto {
        val user = userService.getValidUser(requestDto.email, requestDto.password)
        val article = Article(
            title = requestDto.title,
            content = requestDto.content,
            user = user
        )

        val savedArticle = articleRepository.save(article)

        return ArticleResponseDto(
            articleId = savedArticle.articleId!!,
            email = savedArticle.user.email,
            title = savedArticle.title,
            content = savedArticle.content
        )
    }

    @Transactional
    fun getArticlesByUserId(page: Int, size: Int, sort: String, userId: Long): List<ArticleResponseDto> {
        val pageable: PageRequest = PageRequest.of(page - 1, size, Sort.Direction.DESC, sort)
        val articles = articleRepository.findArticlesByUserUserId(userId, pageable)
        return articles.content.map {
            ArticleResponseDto(
                articleId = it.articleId!!,
                email = it.user.email,
                title = it.title,
                content = it.content
            )
        }
    }

    @Transactional
    fun getArticles(page: Int, size: Int): List<ArticleResponseDto> {
        val pageable: PageRequest = PageRequest.of(page - 1, size, Sort.Direction.DESC, "articleId")
        val articles = articleRepository.findAll(pageable)
        return articles.content.map {
            ArticleResponseDto(
                articleId = it.articleId!!,
                email = it.user.email,
                title = it.title,
                content = it.content
            )
        }
    }

    @Transactional
    fun getArticleByArticleId(articleId: Long): ArticleResponseDto {
        val foundArticle = findValidArticleByArticleId(articleId)
        return ArticleResponseDto(
            articleId = foundArticle.articleId!!,
            email = foundArticle.user.email,
            title = foundArticle.title,
            content = foundArticle.content
        )
    }

    @Transactional
    fun updateArticle(articleId: Long, requestDto: ArticleRequestDto): ArticleResponseDto {
        val requestUser = userService.getValidUser(requestDto.email, requestDto.password)
        val foundArticle = findValidArticleByArticleId(articleId)
        foundArticle.let {
            if (it.user.email != requestUser.email) {
                throw NotArticleOwnerException()
            }
            it.updateTitleAndContent(requestDto.title, requestDto.content)
            return ArticleResponseDto(
                articleId = it.articleId!!,
                email = it.user.email,
                title = it.title,
                content = it.content
            )
        }
    }

    @Transactional
    fun deleteArticle(requestDto: DeleteArticleDto) {
        val requestUser = userService.getValidUser(requestDto.email, requestDto.password)
        val foundArticle = findValidArticleByArticleId(requestDto.articleId)
        foundArticle.let {
            if (it.user.email != requestUser.email) {
                throw NotArticleOwnerException()
            }
            articleRepository.delete(it)
        }
    }

    fun findValidArticleByArticleId(articleId: Long): Article {
        return articleRepository.findArticleByArticleId(articleId) ?: throw ArticleNotFoundException()
    }
}

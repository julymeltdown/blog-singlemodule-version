package com.julymeltdown.blog.api.controller

import com.julymeltdown.blog.api.request.ArticleRequest
import com.julymeltdown.blog.api.request.DeleteArticleRequest
import com.julymeltdown.blog.api.response.ArticleResponse
import com.julymeltdown.blog.application.annotation.Auth
import com.julymeltdown.blog.application.dto.post.ArticleRequestDto
import com.julymeltdown.blog.application.dto.post.DeleteArticleDto
import com.julymeltdown.blog.application.service.post.ArticleService
import com.julymeltdown.blog.infrastructure.security.AuthInfo
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
class ArticleController(
    private val articleService: ArticleService
) {
    @GetMapping("/api/articles")
    fun getArticles(
        @RequestParam("page", defaultValue = "1") page: Int,
        @RequestParam("size", defaultValue = "10") size: Int
    ): ResponseEntity<List<ArticleResponse>> {
        val articles = articleService.getArticles(page, size)
        return ResponseEntity.ok(
            articles.map {
                ArticleResponse(
                    articleId = it.articleId,
                    email = it.email,
                    title = it.title,
                    content = it.content
                )
            }
        )
    }

    @GetMapping("/api/articles/{articleId}")
    fun getArticleByArticleId(
        @PathVariable articleId: Long
    ): ResponseEntity<ArticleResponse> {
        val article = articleService.getArticleByArticleId(articleId)
        return ResponseEntity.ok(
            ArticleResponse(
                articleId = article.articleId,
                email = article.email,
                title = article.title,
                content = article.content
            )
        )
    }
    @PostMapping("/api/articles")
    fun createArticle(
        @Valid @RequestBody articleRequest: ArticleRequest,
        @Auth authInfo: AuthInfo
    ): ResponseEntity<ArticleResponse> {
        val articleRequestDto = ArticleRequestDto(
            email = authInfo.email,
            title = articleRequest.title,
            content = articleRequest.content
        )
        val savedArticle = articleService.createArticle(articleRequestDto)
        return ResponseEntity.ok(
            ArticleResponse(
                articleId = savedArticle.articleId,
                email = savedArticle.email,
                title = savedArticle.title,
                content = savedArticle.content
            )
        )
    }

    @PatchMapping("/api/articles/{articleId}")
    fun updateArticle(
        @PathVariable articleId: Long,
        @Valid @RequestBody articleRequest: ArticleRequest,
        @Auth authInfo: AuthInfo
    ): ResponseEntity<ArticleResponse> {
        val updateArticleRequestDto = ArticleRequestDto(
            email = authInfo.email,
            title = articleRequest.title,
            content = articleRequest.content
        )
        val updatedArticle = articleService.updateArticle(articleId, updateArticleRequestDto)
        return ResponseEntity.ok(
            ArticleResponse(
                articleId = updatedArticle.articleId,
                email = updatedArticle.email,
                title = updatedArticle.title,
                content = updatedArticle.content
            )
        )
    }

    @DeleteMapping("/api/articles/{articleId}")
    fun deleteArticle(
        @PathVariable articleId: Long,
        @Valid @RequestBody deleteArticleRequest: DeleteArticleRequest,
        @Auth authInfo: AuthInfo
    ): ResponseEntity<Unit> {
        val deleteArticleDto = DeleteArticleDto(
            email = authInfo.email,
            password = deleteArticleRequest.password,
            articleId = articleId
        )
        articleService.deleteArticle(deleteArticleDto)
        return ResponseEntity.ok().build()
    }
}
package com.julymeltdown.blog.api.aspect

import com.julymeltdown.blog.api.request.ArticleRequest
import com.julymeltdown.blog.api.request.CommentRequest
import com.julymeltdown.blog.domain.exceptions.InvalidTextException
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Before
import org.springframework.stereotype.Component

@Aspect
@Component
class TextValidationAspect {
    @Before("execution(* com.julymeltdown.blog.api.controller.ArticleController.*(..)) && args(articleRequest,..)")
    fun validateArticleRequest(articleRequest: ArticleRequest) {
        if (articleRequest.title.isNullOrBlank() || articleRequest.content.isNullOrBlank()) {
            throw InvalidTextException()
        }
    }

    @Before("execution(* com.julymeltdown.blog.api.controller.ArticleController.*(..)) && args(*, articleRequest, ..)")
    fun validateUpdatedArticleRequest(articleRequest: ArticleRequest) {
        if (articleRequest.title.isNullOrBlank() || articleRequest.content.isNullOrBlank()) {
            throw InvalidTextException()
        }
    }

    @Before("execution(* com.julymeltdown.blog.api.controller.CommentController.createComment(..)) && args(*, commentRequest)")
    fun validateCreateCommentRequest(commentRequest: CommentRequest) {
        if (commentRequest.content.isNullOrBlank()) {
            throw InvalidTextException()
        }
    }

    @Before("execution(* com.julymeltdown.blog.api.controller.CommentController.updateComment(..)) && args(*, *, commentRequest)")
    fun validateUpdateCommentRequest(commentRequest: CommentRequest) {
        if (commentRequest.content.isNullOrBlank()) {
            throw InvalidTextException()
        }
    }
}

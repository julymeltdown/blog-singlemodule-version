package com.julymeltdown.blog.api.exception

import com.julymeltdown.blog.api.response.ErrorResponse
import com.julymeltdown.blog.domain.exceptions.ArticleNotFoundException
import com.julymeltdown.blog.domain.exceptions.InvalidTextException
import com.julymeltdown.blog.domain.exceptions.code.ErrorCode
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.context.request.ServletWebRequest
import org.springframework.web.context.request.WebRequest

@RestControllerAdvice
class ArticleExceptionHandler {
    @ExceptionHandler(InvalidTextException::class)
    fun handleInvalidTextException(
        exception: InvalidTextException,
        request: WebRequest
    ): ResponseEntity<ErrorResponse> {
        val errorCode: String = ErrorCode.INVALID_TEXT.message
        val uri = (request as ServletWebRequest).request.requestURI
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(
                ErrorResponse(
                    HttpStatus.BAD_REQUEST,
                    errorCode,
                    uri
                )
            )
    }
    @ExceptionHandler(ArticleNotFoundException::class)
    fun handleArticleNotFoundException(
        exception: ArticleNotFoundException,
        request: WebRequest
    ): ResponseEntity<ErrorResponse> {
        val errorCode: String = ErrorCode.NOT_EXISTS_ARTICLE.message
        val uri = (request as ServletWebRequest).request.requestURI
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(
                ErrorResponse(
                    HttpStatus.BAD_REQUEST,
                    errorCode,
                    uri
                )
            )
    }
}
package com.julymeltdown.blog.api.exception

import com.julymeltdown.blog.api.response.ErrorResponse
import com.julymeltdown.blog.domain.exceptions.*
import com.julymeltdown.blog.domain.exceptions.code.ErrorCode
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.context.request.ServletWebRequest
import org.springframework.web.context.request.WebRequest

@RestControllerAdvice
class UserExceptionHandler {
    @ExceptionHandler(UserNotFoundException::class)
    fun handleUserNotFoundException(
        exception: UserNotFoundException,
        request: WebRequest
    ): ResponseEntity<ErrorResponse> {
        val errorCode: String = ErrorCode.NOT_EXISTS_USER.message
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

    @ExceptionHandler(UserPasswordIncorrectException::class)
    fun handleUserPasswordIncorrectException(
        exception: UserPasswordIncorrectException,
        request: WebRequest
    ): ResponseEntity<ErrorResponse> {
        val errorCode: String = ErrorCode.INVALID_USER_PASSWORD.message
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

    @ExceptionHandler(EmailExistException::class)
    fun handleEmailExistException(
        exception: EmailExistException,
        request: WebRequest
    ): ResponseEntity<ErrorResponse> {
        val errorCode: String = ErrorCode.ALREADY_EXISTS_USER.message
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

    @ExceptionHandler(NotArticleOwnerException::class)
    fun handleNotArticleOwnerException(
        exception: NotArticleOwnerException,
        request: WebRequest
    ): ResponseEntity<ErrorResponse> {
        val errorCode: String = ErrorCode.ARTICLE_PERMISSION_DENIED.message
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

    @ExceptionHandler(NotCommentOwnerException::class)
    fun handleNotCommentOwnerException(
        exception: NotCommentOwnerException,
        request: WebRequest
    ): ResponseEntity<ErrorResponse> {
        val errorCode: String = ErrorCode.COMMENT_PERMISSION_DENIED.message
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

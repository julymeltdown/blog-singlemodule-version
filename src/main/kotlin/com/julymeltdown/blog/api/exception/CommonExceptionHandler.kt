package com.julymeltdown.blog.api.exception

import com.fasterxml.jackson.databind.exc.InvalidFormatException
import com.fasterxml.jackson.module.kotlin.MissingKotlinParameterException
import com.julymeltdown.blog.api.response.ErrorResponse
import com.julymeltdown.blog.domain.exceptions.code.ApiErrorCode
import org.springframework.context.MessageSource
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import org.springframework.web.context.request.ServletWebRequest

@RestControllerAdvice
class CommonExceptionHandler(
    private val messageSource: MessageSource,
) : ResponseEntityExceptionHandler() {
    override fun handleHttpMessageNotReadable(
        ex: HttpMessageNotReadableException,
        headers: HttpHeaders,
        status: HttpStatus,
        request: WebRequest
    ): ResponseEntity<Any> {
        val uri = (request as ServletWebRequest).request.requestURI
        when (val exception = ex.cause) {
            is MissingKotlinParameterException -> mutableMapOf<String, MutableList<String>>(
                exception.parameter.name.orEmpty() to mutableListOf(
                    "메세지를 입력해 주세요."
                )
            ).toString()

            is InvalidFormatException -> mutableMapOf<String, MutableList<String>>(
                exception.path.last().fieldName.orEmpty() to mutableListOf(
                    "메세지 형식이 맞지 않습니다."
                )
            ).toString()

            else -> exception?.message.orEmpty()
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(ErrorResponse(HttpStatus.BAD_REQUEST, ApiErrorCode.JSON_CONVERT_ERROR.code, uri))
    }
}

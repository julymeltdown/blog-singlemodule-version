package com.julymeltdown.blog.api.controller

import com.julymeltdown.blog.api.request.CommentRequest
import com.julymeltdown.blog.api.request.DeleteCommentRequest
import com.julymeltdown.blog.api.response.CommentResponse
import com.julymeltdown.blog.application.annotation.Auth
import com.julymeltdown.blog.application.dto.post.CommentRequestDto
import com.julymeltdown.blog.application.dto.post.DeleteCommentDto
import com.julymeltdown.blog.application.service.post.CommentService
import com.julymeltdown.blog.infrastructure.security.AuthInfo
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
class CommentController(
    private val commentService: CommentService
) {
    @GetMapping("/api/articles/{articleId}/comments")
    fun getCommentsByArticleId(
        @PathVariable articleId: Long,
        @RequestParam("page", defaultValue = "1") page: Int,
        @RequestParam("size", defaultValue = "10") size: Int
    ): ResponseEntity<List<CommentResponse>> {
        val comments = commentService.getCommentsByArticleId(articleId, page, size)
        return ResponseEntity.ok(
            comments.map {
                CommentResponse(
                    commentId = it.commentId,
                    email = it.email,
                    content = it.content
                )
            }
        )
    }
    @PostMapping("/api/articles/{articleId}/comments")
    fun createComment(
        @PathVariable articleId: Long,
        @Valid @RequestBody request: CommentRequest,
        @Auth authInfo: AuthInfo
    ): ResponseEntity<CommentResponse> {
        val commentRequestDto = CommentRequestDto(
            articleId = articleId,
            email = authInfo.email,
            content = request.content,
        )
        val comment = commentService.createComment(commentRequestDto)
        return ResponseEntity.ok(
            CommentResponse(
                commentId = comment.commentId,
                email = comment.email,
                content = comment.content
            )
        )
    }

    @PatchMapping("/api/articles/{articleId}/comments/{commentId}")
    fun updateComment(
        @PathVariable articleId: Long,
        @PathVariable commentId: Long,
        @Valid @RequestBody request: CommentRequest,
        @Auth authInfo: AuthInfo
    ): ResponseEntity<CommentResponse> {
        val commentRequestDto = CommentRequestDto(
            articleId = articleId,
            email = authInfo.email,
            content = request.content,
        )
        val comment = commentService.updateComment(commentId, commentRequestDto)
        return ResponseEntity.ok(
            CommentResponse(
                commentId = comment.commentId,
                email = comment.email,
                content = comment.content
            )
        )
    }

    @DeleteMapping("/api/articles/{articleId}/comments/{commentId}")
    fun deleteComment(
        @PathVariable articleId: Long,
        @PathVariable commentId: Long,
        @Valid @RequestBody request: DeleteCommentRequest,
        @Auth authInfo: AuthInfo
    ): ResponseEntity<CommentResponse> {
        val deleteCommentDto = DeleteCommentDto(
            articleId = articleId,
            email = authInfo.email,
            commentId = commentId
        )
        commentService.deleteComment(deleteCommentDto)
        return ResponseEntity.ok().build()
    }
}
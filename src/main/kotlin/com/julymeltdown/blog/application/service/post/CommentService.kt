package com.julymeltdown.blog.application.service.post

import com.julymeltdown.blog.application.dto.post.CommentRequestDto
import com.julymeltdown.blog.application.dto.post.CommentResponseDto
import com.julymeltdown.blog.application.dto.post.DeleteCommentDto
import com.julymeltdown.blog.domain.model.alarm.event.AlarmEvent
import com.julymeltdown.blog.domain.exceptions.CommentNotFoundException
import com.julymeltdown.blog.domain.exceptions.NotCommentOwnerException
import com.julymeltdown.blog.domain.model.posting.entity.Comment
import com.julymeltdown.blog.infrastructure.repository.post.CommentRepository
import org.springframework.context.ApplicationEventPublisher
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class CommentService(
    private val commentRepository: CommentRepository,
    private val userService: UserService,
    private val articleService: ArticleService,
    private val applicationEventPublisher: ApplicationEventPublisher
) {
    @Transactional
    fun createComment(requestDto: CommentRequestDto): CommentResponseDto {
        val requestedUser = userService.getValidUser(requestDto.email, requestDto.password)
        val foundArticle = articleService.findValidArticleByArticleId(requestDto.articleId)
        val savedComment = commentRepository.save(
            Comment(
                article = foundArticle,
                user = requestedUser,
                content = requestDto.content
            )
        )
        val alarmEvent = AlarmEvent(
            savedComment.user.userId!!,
            savedComment.article.articleId!!,
            savedComment.user.userId!!,
            savedComment.commentId!!
        )
        applicationEventPublisher.publishEvent(alarmEvent)
        return CommentResponseDto(
            commentId = savedComment.commentId!!,
            email = savedComment.user.email,
            content = savedComment.content
        )
    }

    @Transactional(readOnly = true)
    fun findCommentsByArticleId(articleId: Long, page: Int, sort: String, size: Int): List<CommentResponseDto> {
        val pageable: PageRequest = PageRequest.of(page - 1, size, Sort.Direction.DESC, sort)
        val foundComments = commentRepository.findCommentsByArticleArticleId(articleId, pageable)
        return foundComments.content.map {
            CommentResponseDto(
                commentId = it.commentId!!,
                email = it.user.email,
                content = it.content
            )
        }
    }

    @Transactional
    fun updateComment(commentId: Long, requestDto: CommentRequestDto): CommentResponseDto {
        val requestedUser = userService.getValidUser(requestDto.email, requestDto.password)
        val foundComment = findValidCommentByCommentId(commentId)
        if (requestedUser.userId != foundComment.user.userId) {
            throw NotCommentOwnerException()
        }
        foundComment.content = requestDto.content
        return CommentResponseDto(
            commentId = foundComment.commentId!!,
            email = foundComment.user.email,
            content = foundComment.content
        )
    }

    @Transactional
    fun deleteComment(requestDto: DeleteCommentDto) {
        val requestedUser = userService.getValidUser(requestDto.email, requestDto.password)
        val foundComment = findValidCommentByCommentId(requestDto.commentId)
        if (requestedUser.userId != foundComment.user.userId) {
            throw NotCommentOwnerException()
        }
        commentRepository.delete(foundComment)
    }

    @Transactional
    fun getCommentsByArticleId(articleId: Long, page: Int, size: Int): List<CommentResponseDto> {
        val pageable: PageRequest = PageRequest.of(page - 1, size, Sort.Direction.DESC, "commentId")
        val foundComments = commentRepository.findCommentsByArticleArticleId(articleId, pageable)
        return foundComments.content.map {
            CommentResponseDto(
                commentId = it.commentId!!,
                email = it.user.email,
                content = it.content
            )
        }
    }

    fun findValidCommentByCommentId(commentId: Long): Comment {
        return commentRepository.findCommentByCommentId(commentId) ?: throw CommentNotFoundException()
    }
}
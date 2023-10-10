package com.julymeltdown.blog.application.service.notification

import com.julymeltdown.blog.application.dto.notification.AlarmDto
import com.julymeltdown.blog.application.dto.notification.AlarmResponseDto
import com.julymeltdown.blog.application.service.post.ArticleService
import com.julymeltdown.blog.application.service.post.CommentService
import com.julymeltdown.blog.application.service.post.UserService
import com.julymeltdown.blog.domain.model.alarm.entity.Alarm
import com.julymeltdown.blog.infrastructure.repository.alarm.AlarmRepository
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class AlarmService(
    private val userService: UserService,
    private val alarmRepository: AlarmRepository,
    private val articleService: ArticleService,
    private val commentService: CommentService,
) {
    @Transactional
    fun saveAlarm(requestDto: AlarmDto) {
        val articleUser = userService.getValidUser(requestDto.articleUserId)
        val commentUser = userService.getValidUser(requestDto.commentUserId)
        val article = articleService.findValidArticleByArticleId(requestDto.articleId)
        val comment = commentService.findValidCommentByCommentId(requestDto.commentId)
        alarmRepository.save(
            Alarm(
                articleUser = articleUser,
                commentUser = commentUser,
                article = article,
                comment = comment
            )
        )
    }

    @Transactional
    fun getAlarms(page: Int, size: Int, userId: Long): List<AlarmResponseDto> {
        val pageable: PageRequest = PageRequest.of(page - 1, size, Sort.Direction.DESC, "alarmId")
        val user = userService.getValidUser(userId)
        val alarms = alarmRepository.findAlarmByArticleUserUserId(user.userId!!, pageable)
        return alarms.content.map {
            val article = articleService.findValidArticleByArticleId(it.article.articleId!!)
            val comment = commentService.findValidCommentByCommentId(it.comment.commentId!!)
            AlarmResponseDto(
                it.alarmId!!,
                article.title,
                comment.content,
            )
        }
    }
}
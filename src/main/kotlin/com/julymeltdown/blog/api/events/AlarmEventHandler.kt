package com.julymeltdown.blog.api.events

import com.julymeltdown.blog.application.dto.notification.AlarmDto
import com.julymeltdown.blog.application.service.notification.AlarmService
import com.julymeltdown.blog.domain.model.alarm.event.AlarmEvent
import org.springframework.context.event.EventListener
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component

@Component
class AlarmEventHandler(
    private val alarmService: AlarmService,
) {
    @EventListener
    @Async
    fun sendAlarm(event: AlarmEvent) {
        val alarmDto = AlarmDto(
            articleUserId = event.articleUserId,
            articleId = event.articleId,
            commentUserId = event.commentUserId,
            commentId = event.commentId
        )
        alarmService.saveAlarm(alarmDto)
    }
}
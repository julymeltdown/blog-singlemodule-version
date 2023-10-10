package com.julymeltdown.blog.domain.model.alarm.event

class AlarmEvent(
    val articleUserId: Long,
    val articleId: Long,
    val commentUserId: Long,
    val commentId: Long,
)
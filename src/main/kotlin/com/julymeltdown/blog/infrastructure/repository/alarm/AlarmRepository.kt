package com.julymeltdown.blog.infrastructure.repository.alarm

import com.julymeltdown.blog.domain.model.alarm.entity.Alarm
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface AlarmRepository : JpaRepository<Alarm, Long> {
    fun findAlarmByArticleUserUserId(userId: Long, pageable: Pageable): Page<Alarm>
}
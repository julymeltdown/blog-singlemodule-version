package com.julymeltdown.blog.api.controller

import com.julymeltdown.blog.api.response.AlarmResponse
import com.julymeltdown.blog.application.service.notification.AlarmService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class AlarmController(
    val alarmService: AlarmService,
) {
    // 나중에 jwt 적용할때 path 안쓰도록 고칠거임
    @GetMapping("/api/alarms/{userId}")
    fun getAlarms(
        @RequestParam("page", defaultValue = "1") page: Int,
        @RequestParam("size", defaultValue = "10") size: Int,
        @PathVariable("userId") userId: Long
    ): ResponseEntity<List<AlarmResponse>> {
        val alarms = alarmService.getAlarms(page, size, userId)
        return ResponseEntity.ok(
            alarms.map {
                AlarmResponse(
                    alarmId = it.alarmId,
                    articleTitle = it.articleTitle,
                    commentContent = it.commentContent,
                )
            }
        )
    }
}
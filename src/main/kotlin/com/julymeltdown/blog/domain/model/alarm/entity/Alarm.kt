package com.julymeltdown.blog.domain.model.alarm.entity

import com.julymeltdown.blog.domain.model.posting.entity.Article
import com.julymeltdown.blog.domain.model.posting.entity.Comment
import com.julymeltdown.blog.domain.model.posting.entity.User
import javax.persistence.*

@Entity
@Table(name = "alarms")
class Alarm(
    @field:Id
    @field:GeneratedValue(strategy = GenerationType.IDENTITY)
    val alarmId: Long? = 0L,

    @field:ManyToOne(fetch = FetchType.LAZY)
    @field:JoinColumn(name = "article_user_id")
    var articleUser: User,

    @field:ManyToOne(fetch = FetchType.LAZY)
    @field:JoinColumn(name = "article_id")
    var article: Article,

    @field:ManyToOne(fetch = FetchType.LAZY)
    @field:JoinColumn(name = "comment_user_id")
    var commentUser: User,

    @field:OneToOne(fetch = FetchType.LAZY)
    @field:JoinColumn(name = "comment_id")
    var comment: Comment,
)

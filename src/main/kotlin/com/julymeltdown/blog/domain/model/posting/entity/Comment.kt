package com.julymeltdown.blog.domain.model.posting.entity

import com.julymeltdown.blog.domain.model.common.entity.BaseTimeEntity
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.Lob
import javax.persistence.ManyToOne
import javax.persistence.Table

@Entity
@Table(name = "comments")
class Comment(
    @field:Id
    @field:GeneratedValue(strategy = GenerationType.IDENTITY)
    val commentId: Long? = 0L,

    @field:ManyToOne(fetch = FetchType.LAZY)
    @field:JoinColumn(name = "article_id")
    val article: Article,

    @field:ManyToOne(fetch = FetchType.LAZY)
    @field:JoinColumn(name = "user_id")
    val user: User,

    @field:Lob
    @field:Column(name = "content")
    var content: String
) : BaseTimeEntity()

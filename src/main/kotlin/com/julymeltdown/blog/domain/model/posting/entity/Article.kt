package com.julymeltdown.blog.domain.model.posting.entity

import com.julymeltdown.blog.domain.model.common.entity.BaseTimeEntity
import org.hibernate.annotations.DynamicUpdate
import javax.persistence.CascadeType
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.Lob
import javax.persistence.ManyToOne
import javax.persistence.OneToMany
import javax.persistence.Table

@Entity
@Table(name = "articles")
@DynamicUpdate
class Article(
    @field:Id
    @field:GeneratedValue(strategy = GenerationType.IDENTITY)
    val articleId: Long? = 0L,

    @field:ManyToOne(fetch = FetchType.LAZY)
    @field:JoinColumn(name = "user_id")
    var user: User,

    @field:Lob
    @field:Column(name = "content")
    var content: String,

    @field:Column(name = "title")
    var title: String
) : BaseTimeEntity() {
    // 고아 객체(comments) 삭제용
    @field:OneToMany(mappedBy = "article", cascade = [CascadeType.REMOVE])
    val comments: List<Comment> = mutableListOf()

    fun updateTitleAndContent(title: String, content: String) {
        this.title = title
        this.content = content
    }
}

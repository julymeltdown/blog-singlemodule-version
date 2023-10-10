package com.julymeltdown.blog.domain.model.posting.entity

import com.julymeltdown.blog.domain.model.common.entity.BaseTimeEntity
import javax.persistence.CascadeType
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.OneToMany
import javax.persistence.Table

@Entity
@Table(name = "users")
class User(
    @field:Id
    @field:GeneratedValue(strategy = GenerationType.IDENTITY)
    val userId: Long? = 0L,

    @field:Column(unique = true, name = "email")
    val email: String,

    @field:Column(name = "password", nullable = false)
    val password: String,

    @field:Column(name = "username", nullable = false)
    val username: String,

    @field:Column(name = "refresh_token")
    val refreshToken: String

) : BaseTimeEntity() {
    // 고아 객체(articles, comments) 삭제용
    @field:OneToMany(mappedBy = "user", cascade = [CascadeType.REMOVE])
    val articles: List<Article> = mutableListOf()

    @field:OneToMany(mappedBy = "user", cascade = [CascadeType.REMOVE])
    val comments: List<Comment> = mutableListOf()
}

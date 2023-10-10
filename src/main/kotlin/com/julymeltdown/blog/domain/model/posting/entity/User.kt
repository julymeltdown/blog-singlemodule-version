package com.julymeltdown.blog.domain.model.posting.entity

import com.julymeltdown.blog.domain.model.common.entity.BaseTimeEntity
import com.julymeltdown.blog.domain.model.posting.enums.Role
import javax.persistence.*

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
    var refreshToken: String? = null,

    @field:Column(name = "role", nullable = false)
    @field:Enumerated(EnumType.STRING)
    val role: Role = Role.USER

) : BaseTimeEntity() {
    fun updateRefreshToken(refreshToken: String) {
        this.refreshToken = refreshToken
    }

    // 고아 객체(articles, comments) 삭제용
    @field:OneToMany(mappedBy = "user", cascade = [CascadeType.REMOVE])
    val articles: List<Article> = mutableListOf()

    @field:OneToMany(mappedBy = "user", cascade = [CascadeType.REMOVE])
    val comments: List<Comment> = mutableListOf()
}

package com.julymeltdown.blog.infrastructure.repository.post

import com.julymeltdown.blog.domain.model.posting.entity.User
import com.julymeltdown.blog.domain.model.posting.repository.CustomUserRepository
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : JpaRepository<User, Long>, CustomUserRepository
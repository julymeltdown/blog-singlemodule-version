package com.julymeltdown.blog.infrastructure.repository.post

import com.julymeltdown.blog.domain.model.posting.entity.Comment
import com.julymeltdown.blog.domain.model.posting.repository.CustomCommentRepository
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CommentRepository : JpaRepository<Comment, Long>, CustomCommentRepository
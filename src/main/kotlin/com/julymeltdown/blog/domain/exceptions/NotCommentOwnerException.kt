package com.julymeltdown.blog.domain.exceptions

class NotCommentOwnerException() : RuntimeException("댓글 작성자가 아닙니다")
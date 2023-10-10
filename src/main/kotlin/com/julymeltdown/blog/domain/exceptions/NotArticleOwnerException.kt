package com.julymeltdown.blog.domain.exceptions

class NotArticleOwnerException : RuntimeException("해당 게시글의 작성자가 아닙니다.")
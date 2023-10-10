package com.julymeltdown.blog.api.request

import javax.validation.constraints.Pattern

data class RegisterRequest(
    @field:Pattern(regexp = EMAIL_REGEX, message = "이메일 형식이 올바르지 않습니다.")
    val email: String,
    @field:Pattern(regexp = PASSWORD_REGEX, message = "비밀번호는 8자 이상, 숫자와 문자를 포함해야 합니다.")
    val password: String,
    @field:Pattern(regexp = USERNAME_REGEX, message = "닉네임은 2자 이상 10자 이하의 영문자와 숫자로 이루어져야 합니다.")
    val username: String,
    @field:Pattern(regexp = ROLE_REGEX, message = "USER 또는 ADMIN만 가능합니다.")
    val role: String
) {
    companion object {
        const val EMAIL_REGEX = "^[a-zA-Z0-9]+@[a-zA-Z0-9]+\\.[a-zA-Z0-9]+$"
        const val PASSWORD_REGEX = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$"
        const val USERNAME_REGEX = "[A-Za-z|가-힣|ㄱ-ㅎ|ㅏ-ㅣ\\d\\s]{2,12}\$"
        const val ROLE_REGEX = "^(USER|ADMIN)\$"
    }
}
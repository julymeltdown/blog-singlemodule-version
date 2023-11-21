package com.julymeltdown.blog.api.controller

import com.julymeltdown.blog.api.request.LoginRequest
import com.julymeltdown.blog.api.request.RegisterRequest
import com.julymeltdown.blog.api.response.LoginResponse
import com.julymeltdown.blog.api.response.RegisterResponse
import com.julymeltdown.blog.application.annotation.Auth
import com.julymeltdown.blog.application.dto.user.DeleteUserDto
import com.julymeltdown.blog.application.dto.user.LoginRequestDto
import com.julymeltdown.blog.application.dto.user.RegisterRequestDto
import com.julymeltdown.blog.application.service.post.UserService
import com.julymeltdown.blog.infrastructure.security.AuthInfo
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
class UserController(
    private val userService: UserService
) {
    @GetMapping("/api/users/admin")
    fun getAdmin(
    ): ResponseEntity<RegisterResponse>{
        val user = userService.getAdminUser()
        return ResponseEntity.ok().body(
            RegisterResponse(
                email = user.email,
                username = user.username,
                role = user.role.toString()
            )
        )
    }

    @PostMapping("/api/users")
    fun signUp(
        @Valid @RequestBody request: RegisterRequest
    ): ResponseEntity<RegisterResponse> {
        val savedUser = userService.registerUser(
            RegisterRequestDto(
                email = request.email,
                password = request.password,
                username = request.username
            )
        )
        return ResponseEntity.ok().body(
            RegisterResponse(
                email = savedUser.email,
                username = savedUser.username,
                role = savedUser.role
            )
        )
    }

    @PostMapping("/api/users/login")
    fun login(
        @Valid @RequestBody request: LoginRequest
    ): ResponseEntity<LoginResponse> {
        val savedUser = userService.loginUser(
            LoginRequestDto(
                email = request.email,
                password = request.password,
            )
        )
        return ResponseEntity.ok().body(
            LoginResponse(
                accessToken = savedUser.accessToken,
                refreshToken = savedUser.refreshToken
            )
        )
    }

    @DeleteMapping("/api/users")
    fun deleteUser(
        @Auth authInfo: AuthInfo,
    ): ResponseEntity<RegisterResponse> {
        userService.deleteUser(
            DeleteUserDto(
                email = authInfo.email,
            )
        )
        return ResponseEntity.ok().build()
    }
}
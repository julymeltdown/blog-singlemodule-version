package com.julymeltdown.blog.application.service.post

import com.julymeltdown.blog.application.dto.post.*
import com.julymeltdown.blog.application.dto.user.*
import com.julymeltdown.blog.domain.exceptions.EmailExistException
import com.julymeltdown.blog.domain.exceptions.UserNotFoundException
import com.julymeltdown.blog.domain.exceptions.UserPasswordIncorrectException
import com.julymeltdown.blog.domain.model.posting.entity.User
import com.julymeltdown.blog.domain.model.posting.enums.Role
import com.julymeltdown.blog.infrastructure.repository.post.UserRepository
import com.julymeltdown.blog.infrastructure.security.JwtTokenProvider
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserService(
    private val userRepository: UserRepository,
    private val bCryptPasswordEncoder: BCryptPasswordEncoder,
    private val jwtTokenProvider: JwtTokenProvider
) {
    @Transactional
    fun registerUser(registerRequestDto: RegisterRequestDto): RegisterResponseDto {
        if (isEmailExist(registerRequestDto.email)) {
            throw EmailExistException()
        }
        val savedUser = userRepository.save(
            User(
                email = registerRequestDto.email,
                password = bCryptPasswordEncoder.encode(registerRequestDto.password),
                username = registerRequestDto.username,
                role = Role.USER
            )
        )

        return RegisterResponseDto(
            email = savedUser.email,
            username = savedUser.username,
            role = savedUser.role.toString()
        )
    }

    @Transactional
    fun loginUser(loginRequestDto: LoginRequestDto): LoginResponseDto {
        val user = userRepository.findByEmail(loginRequestDto.email) ?: throw UserNotFoundException()
        if (!bCryptPasswordEncoder.matches(loginRequestDto.password, user.password)) throw UserPasswordIncorrectException()

        val accessToken = jwtTokenProvider.generateAccessToken(user.email)
        val refreshToken = jwtTokenProvider.generateRefreshToken(user.email)

        user.refreshToken = refreshToken.token
        return LoginResponseDto(
            accessToken = accessToken.token,
            refreshToken = refreshToken.token,
        )
    }

    @Transactional
    fun deleteUser(requestDto: DeleteUserDto) {
        val user = getValidUser(requestDto.email)
        userRepository.delete(user)
    }

    fun getValidUser(email: String): User {
        return userRepository.findByEmail(email) ?: throw UserNotFoundException()
    }
    fun getValidUserByEmail(email: String): User {
        return userRepository.findByEmail(email) ?: throw UserNotFoundException()
    }
    fun getValidUser(userId: Long): User {
        return userRepository.findById(userId).orElseThrow { UserNotFoundException() }
    }
    private fun isEmailExist(email: String): Boolean {
        return userRepository.existsByEmail(email)
    }
}
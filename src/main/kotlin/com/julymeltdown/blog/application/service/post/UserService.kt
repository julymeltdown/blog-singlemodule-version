package com.julymeltdown.blog.application.service.post

import com.julymeltdown.blog.application.dto.post.DeleteUserDto
import com.julymeltdown.blog.application.dto.post.RegisterRequestDto
import com.julymeltdown.blog.application.dto.post.RegisterResponseDto
import com.julymeltdown.blog.domain.exceptions.EmailExistException
import com.julymeltdown.blog.domain.exceptions.UserNotFoundException
import com.julymeltdown.blog.domain.exceptions.UserPasswordIncorrectException
import com.julymeltdown.blog.domain.model.posting.entity.User
import com.julymeltdown.blog.infrastructure.repository.post.UserRepository
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserService(
    private val userRepository: UserRepository,
    private val bCryptPasswordEncoder: BCryptPasswordEncoder
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
                username = registerRequestDto.username
            )
        )

        return RegisterResponseDto(
            email = savedUser.email,
            username = savedUser.username
        )
    }
    @Transactional
    fun deleteUser(requestDto: DeleteUserDto) {
        val user = getValidUser(requestDto.email, requestDto.password)
        userRepository.delete(user)
    }

    fun getValidUser(email: String, password: String): User {
        return userRepository.findByEmail(email)?.let {
            if (bCryptPasswordEncoder.matches(password, it.password)) {
                it
            } else {
                throw UserPasswordIncorrectException()
            }
        } ?: throw UserNotFoundException()
    }

    fun getValidUser(userId: Long): User {
        return userRepository.findById(userId).orElseThrow { UserNotFoundException() }
    }
    private fun isEmailExist(email: String): Boolean {
        return userRepository.existsByEmail(email)
    }
}
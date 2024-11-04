package com.rayadev.domain.usecase

import com.rayadev.domain.model.User
import com.rayadev.domain.repository.UserRepository

class GetUserUseCase(private val userRepository: UserRepository) {
    suspend operator fun invoke(perPage: Int = 6): List<User> {
        return userRepository.getUsers(perPage)
    }
}
package com.rayadev.domain.usecase

import com.rayadev.domain.model.User
import com.rayadev.domain.model.UserResponse
import com.rayadev.domain.repository.UserRepository

class GetUserUseCase(private val userRepository: UserRepository) {
    suspend operator fun invoke(page: Int, perPage: Int): Result<UserResponse> {
        return try {
            val response = userRepository.getUsers(page, perPage)
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
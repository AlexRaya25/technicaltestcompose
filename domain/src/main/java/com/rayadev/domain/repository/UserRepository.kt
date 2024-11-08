package com.rayadev.domain.repository

import com.rayadev.domain.model.User
import com.rayadev.domain.model.UserResponse

interface UserRepository {
    suspend fun getUsers(page: Int, perPage: Int): UserResponse
}
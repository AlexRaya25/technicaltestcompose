package com.rayadev.domain.repository

import com.rayadev.domain.model.UserResponse

interface UserRepository {
    @Throws(Exception::class)
    suspend fun getUsers(page: Int, perPage: Int): UserResponse
}
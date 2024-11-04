package com.rayadev.domain.repository

import com.rayadev.domain.model.User

interface UserRepository {
    suspend fun getUsers(perPage: Int = 6): List<User>
}
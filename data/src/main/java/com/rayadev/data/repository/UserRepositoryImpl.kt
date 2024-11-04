package com.rayadev.data.repository

import com.rayadev.data.local.UserDao
import com.rayadev.data.local.entity.UserEntity
import com.rayadev.data.mapper.toDomain
import com.rayadev.data.mapper.toEntity
import com.rayadev.data.model.UserResponse
import com.rayadev.data.remote.ApiService
import com.rayadev.domain.model.User
import com.rayadev.domain.repository.UserRepository
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val userDao: UserDao
) : UserRepository {

    override suspend fun getUsers(perPage: Int): List<User> {
        val localUsers = userDao.getAllUsers().map { it.toDomain() }

        if (localUsers.isEmpty()) {
            val response = apiService.getUsers(perPage)
            insertUsers(response.data.map { it.toDomain() })
        }

        return userDao.getAllUsers().map { it.toDomain() }
    }

    suspend fun insertUsers(users: List<User>) {
        userDao.insertUsers(users.map { it.toEntity() })
    }
}
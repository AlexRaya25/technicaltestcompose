package com.rayadev.data.repository

import com.rayadev.data.local.UserDao
import com.rayadev.data.mapper.toDomain
import com.rayadev.data.mapper.toEntity
import com.rayadev.data.remote.ApiService
import com.rayadev.domain.model.UserResponse
import com.rayadev.domain.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val userDao: UserDao
) : UserRepository {

    override suspend fun getUsers(page: Int, perPage: Int): UserResponse = withContext(Dispatchers.IO) {
        try {
            val response = apiService.getUsers(page, perPage)
            userDao.insertUsers(response.data.map { it.toEntity() })

            val paginatedUsers = userDao.getPagedUsers((page - 1) * perPage, perPage).map { it.toDomain() }
            return@withContext UserResponse(paginatedUsers, response.total_pages)
        } catch (e: Exception) {
            throw e
        }
    }
}
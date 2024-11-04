package com.rayadev.data.remote

import com.rayadev.data.model.UserResponse
import retrofit2.http.GET
import retrofit2.http.Path
import com.rayadev.data.remote.ApiResponse
import retrofit2.http.Headers
import retrofit2.http.Query

interface ApiService {

    @Headers("Content-Type: application/json; charset=UTF-8")
    @GET("users")
    suspend fun getUsers(
        @Query("per_page") perPage: Int,
    ): ApiResponse
}
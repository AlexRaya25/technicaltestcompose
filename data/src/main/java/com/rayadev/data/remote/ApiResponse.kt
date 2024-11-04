package com.rayadev.data.remote

import com.rayadev.data.model.UserResponse
import com.rayadev.domain.model.User

data class ApiResponse(
    val page: Int,
    val per_page: Int,
    val total: Int,
    val total_pages: Int,
    val data: List<UserResponse>
)
package com.rayadev.domain.model

data class UserResponse(
    val users: List<User>,
    val totalPages: Int
)
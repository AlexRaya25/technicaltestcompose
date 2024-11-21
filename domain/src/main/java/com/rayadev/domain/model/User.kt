package com.rayadev.domain.model

data class User(
    val id: Int,
    val first_name: String,
    val last_name: String,
    val email: String,
    val avatar: String
){
    companion object {
        fun default(): User {
            return User(
                id = 0,
                first_name = "Unknown",
                last_name = "Unknown",
                email = "unknown@example.com",
                avatar = "default_avatar_url"
            )
        }
    }
}
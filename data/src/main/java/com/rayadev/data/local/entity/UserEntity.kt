package com.rayadev.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey val id: Int,
    val first_name: String,
    val last_name: String,
    val email: String,
    val avatar: String
)
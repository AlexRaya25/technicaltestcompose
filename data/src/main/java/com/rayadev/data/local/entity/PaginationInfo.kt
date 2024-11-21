package com.rayadev.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pagination_info")
data class PaginationInfo(
    @PrimaryKey val id: Int = 1,
    val totalPages: Int
)
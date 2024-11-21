package com.rayadev.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.rayadev.data.local.entity.PaginationInfo
import com.rayadev.data.local.entity.UserEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Query("SELECT * FROM users WHERE id = :userId LIMIT 1")
    fun getUserById(userId: Int): Flow<UserEntity?>

    @Query("SELECT * FROM users LIMIT :limit OFFSET :offset")
    suspend fun getPagedUsers(offset: Int, limit: Int): List<UserEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUsers(users: List<UserEntity>)

    @Query("SELECT * FROM pagination_info WHERE id = 1")
    suspend fun getPaginationInfo(): PaginationInfo?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPaginationInfo(paginationInfo: PaginationInfo)
}
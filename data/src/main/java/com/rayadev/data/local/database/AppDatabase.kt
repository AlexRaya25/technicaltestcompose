package com.rayadev.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.rayadev.data.local.UserDao
import com.rayadev.data.local.entity.UserEntity

@Database(entities = [UserEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}
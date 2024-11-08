package com.rayadev.data.mapper

import com.rayadev.data.local.entity.UserEntity
import com.rayadev.domain.model.User

fun UserEntity.toDomain(): User = User(id, first_name, last_name, email, avatar)

fun User.toEntity(): UserEntity = UserEntity(id, first_name, last_name, email, avatar)
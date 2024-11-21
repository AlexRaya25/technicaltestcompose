package com.rayadev.data.mapper

import com.rayadev.data.local.entity.UserEntity
import com.rayadev.domain.model.User

fun UserEntity.toDomain(): User {
    return User(
        id = this.id,
        first_name = this.first_name,
        last_name = this.last_name,
        email = this.email,
        avatar = this.avatar
    )
}

fun User.toEntity(): UserEntity {
    return UserEntity(
        id = this.id,
        first_name = this.first_name,
        last_name = this.last_name,
        email = this.email,
        avatar = this.avatar
    )
}


package com.rayadev.data.di

import com.rayadev.domain.repository.UserRepository
import com.rayadev.data.repository.UserRepositoryImpl
import com.rayadev.domain.usecase.GetUserUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

    @Module
    @InstallIn(SingletonComponent::class)
    object DomainModule {

        @Provides
        @Singleton
        fun provideUserRepository(
            userRepositoryImpl: UserRepositoryImpl
        ): UserRepository = userRepositoryImpl

        @Provides
        @Singleton
        fun provideGetUserUseCase(
            userRepository: UserRepository
        ): GetUserUseCase = GetUserUseCase(userRepository)
    }

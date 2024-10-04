package com.example.betschallenge.data.di

import com.example.betschallenge.data.repository.BetDetailByFilterRepositoryImpl
import com.example.betschallenge.data.repository.BetsRepositoryImpl
import com.example.betschallenge.data.repository.UserRepositoryImpl
import com.example.betschallenge.domain.repository.BetDetailByFilterRepository
import com.example.betschallenge.domain.repository.BetsRepository
import com.example.betschallenge.domain.repository.UserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun bindBetsRepository(
        repositoryImpl: BetsRepositoryImpl

    ): BetsRepository

    @Binds
    abstract fun bindBetsDetailByFilterRepository(
        repositoryImpl: BetDetailByFilterRepositoryImpl

    ): BetDetailByFilterRepository

    @Binds
    abstract fun bindUserRepository(
        repositoryImpl: UserRepositoryImpl

    ): UserRepository
}
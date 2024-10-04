package com.example.betschallenge.domain.di

import com.example.betschallenge.domain.usecase.bet_detail.GetBetDetailByFilterUseCase
import com.example.betschallenge.domain.usecase.bet_detail.GetBetDetailByFilterUseCaseImpl
import com.example.betschallenge.domain.usecase.bets.GetBetsUseCase
import com.example.betschallenge.domain.usecase.bets.GetBetsUseCaseImpl
import com.example.betschallenge.domain.usecase.user.CreateUserUseCase
import com.example.betschallenge.domain.usecase.user.CreateUserUseCaseImpl
import com.example.betschallenge.domain.usecase.user.LoginUseCase
import com.example.betschallenge.domain.usecase.user.LoginUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
abstract class UseCaseModule {
    @ViewModelScoped
    @Binds
    abstract fun bindUseCaseBets(
        useCaseImpl: GetBetsUseCaseImpl
    ): GetBetsUseCase

    @Binds
    abstract fun bindUseCaseBetDetailByFilter(
        useCaseImpl: GetBetDetailByFilterUseCaseImpl
    ): GetBetDetailByFilterUseCase

    @Binds
    abstract fun bindUseCaseLogin(
        useCaseImpl: LoginUseCaseImpl
    ): LoginUseCase

    @Binds
    abstract fun bindUseCaseCreateUser(
        useCaseImpl: CreateUserUseCaseImpl
    ): CreateUserUseCase
}
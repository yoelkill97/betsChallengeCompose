package com.example.betschallenge.domain.usecase.user

import com.example.betschallenge.domain.entity.user.UserEntity
import com.example.domain.entity.Either
import com.example.domain.entity.Failure

interface CreateUserUseCase {
    suspend fun invoke(correo: String, password: String) : Either<Failure, UserEntity>
}
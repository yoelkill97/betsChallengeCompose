package com.example.betschallenge.domain.usecase.user

import com.example.betschallenge.domain.entity.user.UserEntity
import com.example.betschallenge.domain.repository.UserRepository
import com.example.domain.entity.Either
import com.example.domain.entity.Failure
import javax.inject.Inject

class LoginUseCaseImpl @Inject constructor(private val repository : UserRepository) : LoginUseCase {
    override suspend fun invoke(correo: String, password: String): Either<Failure, UserEntity> {
        return repository.getUser(correo, password)
    }
}
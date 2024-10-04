package com.example.betschallenge.domain.usecase.user

import com.example.betschallenge.domain.entity.user.UserEntity
import com.example.betschallenge.domain.repository.UserRepository
import com.example.domain.entity.Either
import com.example.domain.entity.Failure
import javax.inject.Inject

class CreateUserUseCaseImpl@Inject constructor(private val userRepository: UserRepository) : CreateUserUseCase {
    override suspend fun invoke(email: String, password: String): Either<Failure, UserEntity> {
        return userRepository.createUser(email, password)
    }
}
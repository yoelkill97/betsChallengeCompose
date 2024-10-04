package com.example.betschallenge.domain.repository

import com.example.betschallenge.domain.entity.user.UserEntity
import com.example.domain.entity.Either
import com.example.domain.entity.Failure

interface UserRepository {
    suspend fun getUser(email: String, password: String): Either<Failure, UserEntity>
    suspend fun createUser(email: String, password: String): Either<Failure, UserEntity>
}
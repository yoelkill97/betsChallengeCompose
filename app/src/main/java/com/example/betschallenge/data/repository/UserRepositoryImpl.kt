package com.example.betschallenge.data.repository

import com.example.betschallenge.data.network.mapper.user.UserMapper
import com.example.betschallenge.data.network.source.IUserSource
import com.example.betschallenge.domain.entity.user.UserEntity
import com.example.betschallenge.domain.repository.UserRepository
import com.example.domain.entity.Either
import com.example.domain.entity.Failure
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(private val userSource: IUserSource,private val userMapper: UserMapper) : UserRepository {
    override suspend fun getUser(correo: String, password: String): Either<Failure, UserEntity> {
        return when(val response = userSource.getUser(correo, password)){
            is Either.Right -> Either.Right(userMapper.userDataToDomain(response.b))
            is Either.Left -> Either.Left(response.a)
        }
    }

    override suspend fun createUser(email: String, password: String): Either<Failure, UserEntity> {
        return when(val response = userSource.createUser(email, password)){
            is Either.Right -> Either.Right(userMapper.userDataToDomain(response.b))
            is Either.Left -> Either.Left(response.a)
        }
    }
}
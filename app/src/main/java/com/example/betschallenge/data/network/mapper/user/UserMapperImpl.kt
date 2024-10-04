package com.example.betschallenge.data.network.mapper.user

import com.example.betschallenge.data.entity.UserResponse
import com.example.betschallenge.domain.entity.user.UserEntity
import javax.inject.Inject

class UserMapperImpl@Inject constructor() : UserMapper {
    override suspend fun userDataToDomain(user: UserResponse): UserEntity {
        return UserEntity(
            user.correo,
            user.name
        )
    }
}
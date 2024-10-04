package com.example.betschallenge.data.network.mapper.user

import com.example.betschallenge.data.entity.UserResponse
import com.example.betschallenge.domain.entity.user.UserEntity

interface UserMapper {
    suspend fun userDataToDomain(user: UserResponse): UserEntity
}
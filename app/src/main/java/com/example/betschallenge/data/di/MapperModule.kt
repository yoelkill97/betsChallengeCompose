package com.example.betschallenge.data.di

import com.example.betschallenge.data.network.mapper.bets_detail.BetsDetailMapper
import com.example.betschallenge.data.network.mapper.bets_detail.BetsDetailMapperImpl
import com.example.betschallenge.data.network.mapper.mapperBets.BetsMapper
import com.example.betschallenge.data.network.mapper.mapperBets.BetsMapperImpl
import com.example.betschallenge.data.network.mapper.user.UserMapper
import com.example.betschallenge.data.network.mapper.user.UserMapperImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class MapperModule {
    @Binds
    abstract fun bindMapperBets(
        mapperImpl: BetsMapperImpl
    ) : BetsMapper
    @Binds
    abstract fun bindMapperBetsDetail(
        mapperImpl: BetsDetailMapperImpl
    ) : BetsDetailMapper

    @Binds
    abstract fun bindMapperUser(
        mapperImpl: UserMapperImpl
    ) : UserMapper
}
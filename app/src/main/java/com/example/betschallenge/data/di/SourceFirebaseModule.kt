package com.example.betschallenge.data.di

import com.example.betschallenge.data.network.source.IBetsByFilterSource
import com.example.betschallenge.data.network.source.IBetsSource
import com.example.betschallenge.data.network.source.IUserSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class SourceFirebaseModule {
     @Binds
     abstract fun bindSourceBetsFirebase(
         betsSourceImpl: IBetsSource.IBetsSourceImpl
    ) : IBetsSource

     @Binds
     abstract fun bindSourceBetsDetailFirebase (
         betsDetailSourceImpl: IBetsByFilterSource.IBetsByFilterSourceImpl
        ) : IBetsByFilterSource

     @Binds
     abstract fun bindSourceUserFirebase (
         userSourceImpl: IUserSource.IUserSourceImpl
        ) : IUserSource
}
package com.example.betschallenge.data.repository

import com.example.betschallenge.data.network.mapper.mapperBets.BetsMapper
import com.example.betschallenge.data.network.source.IBetsSource
import com.example.betschallenge.domain.entity.bets.BetItemEntity
import com.example.betschallenge.domain.repository.BetsRepository
import com.example.domain.entity.Either
import com.example.domain.entity.Failure
import javax.inject.Inject

class BetsRepositoryImpl @Inject constructor( private val betSource: IBetsSource , private val betmapper: BetsMapper) : BetsRepository {
    override suspend fun getBets(): Either<Failure, List<BetItemEntity>> {
        return when(val response = betSource.getBets()){
            is Either.Right -> Either.Right(betmapper.betsDataToDomain(response.b))
            is Either.Left -> Either.Left(response.a)
        }
    }
}
package com.example.betschallenge.data.repository

import com.example.betschallenge.data.network.mapper.bets_detail.BetsDetailMapper
import com.example.betschallenge.data.network.source.IBetsByFilterSource
import com.example.betschallenge.domain.entity.bets_detail.BetDetailEntity
import com.example.betschallenge.domain.repository.BetDetailByFilterRepository
import com.example.domain.entity.Either
import com.example.domain.entity.Failure
import javax.inject.Inject

class BetDetailByFilterRepositoryImpl @Inject constructor(private val betsByFilterSource: IBetsByFilterSource, private val betmapper: BetsDetailMapper) : BetDetailByFilterRepository {
    override suspend fun getBetDetailByFilter(filter: String): Either<Failure, List<BetDetailEntity>> {
        return when (val response = betsByFilterSource.getBetsByFilter(filter)) {
            is Either.Right -> Either.Right(betmapper.betsDetailDataToDomain(response.b))
            is Either.Left -> Either.Left(response.a)
        }
    }

}
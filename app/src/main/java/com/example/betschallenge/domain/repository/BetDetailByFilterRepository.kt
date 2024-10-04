package com.example.betschallenge.domain.repository

import com.example.betschallenge.domain.entity.bets_detail.BetDetailEntity
import com.example.domain.entity.Either
import com.example.domain.entity.Failure

interface BetDetailByFilterRepository {
    suspend fun getBetDetailByFilter(filter: String): Either<Failure, List<BetDetailEntity>>
}
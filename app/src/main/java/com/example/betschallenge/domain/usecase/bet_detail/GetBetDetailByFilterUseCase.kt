package com.example.betschallenge.domain.usecase.bet_detail

import com.example.betschallenge.domain.entity.bets_detail.BetDetailEntity
import com.example.domain.entity.Either
import com.example.domain.entity.Failure

interface GetBetDetailByFilterUseCase {
    suspend fun invoke(filter: String): Either<Failure, List<BetDetailEntity>>
}
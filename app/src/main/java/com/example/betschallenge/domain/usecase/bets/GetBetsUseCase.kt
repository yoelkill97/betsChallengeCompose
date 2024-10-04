package com.example.betschallenge.domain.usecase.bets

import com.example.betschallenge.domain.entity.bets.BetItemEntity
import com.example.domain.entity.Either
import com.example.domain.entity.Failure

interface GetBetsUseCase {
    suspend fun invoke() : Either<Failure, List<BetItemEntity>>
}
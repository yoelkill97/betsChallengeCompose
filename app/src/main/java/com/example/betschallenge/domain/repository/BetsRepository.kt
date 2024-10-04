package com.example.betschallenge.domain.repository

import com.example.betschallenge.domain.entity.bets.BetItemEntity
import com.example.domain.entity.Either
import com.example.domain.entity.Failure

interface BetsRepository {
    suspend fun getBets() : Either<Failure,List<BetItemEntity>>
}
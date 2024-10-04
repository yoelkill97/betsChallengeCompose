package com.example.betschallenge.domain.usecase.bets

import com.example.betschallenge.domain.entity.bets.BetItemEntity
import com.example.betschallenge.domain.repository.BetsRepository
import com.example.domain.entity.Either
import com.example.domain.entity.Failure
import javax.inject.Inject

class GetBetsUseCaseImpl @Inject constructor(private val repository: BetsRepository) :
    GetBetsUseCase {
    override suspend fun invoke(): Either<Failure, List<BetItemEntity>> {
        return repository.getBets()
    }
}
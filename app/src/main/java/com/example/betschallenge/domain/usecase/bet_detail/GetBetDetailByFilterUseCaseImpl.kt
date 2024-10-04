package com.example.betschallenge.domain.usecase.bet_detail

import com.example.betschallenge.domain.entity.bets_detail.BetDetailEntity
import com.example.betschallenge.domain.repository.BetDetailByFilterRepository
import com.example.domain.entity.Either
import com.example.domain.entity.Failure
import javax.inject.Inject

class GetBetDetailByFilterUseCaseImpl @Inject constructor(private val repository: BetDetailByFilterRepository) :
    GetBetDetailByFilterUseCase {
    override suspend fun invoke(filter: String): Either<Failure, List<BetDetailEntity>> {
        return repository.getBetDetailByFilter(filter)
    }
}
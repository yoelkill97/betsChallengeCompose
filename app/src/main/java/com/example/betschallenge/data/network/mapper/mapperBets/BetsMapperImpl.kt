package com.example.betschallenge.data.network.mapper.mapperBets

import com.example.betschallenge.data.entity.BetsResponseItem
import com.example.betschallenge.domain.entity.bets.BetItemEntity
import javax.inject.Inject

class BetsMapperImpl @Inject constructor() : BetsMapper {

    override suspend fun betsDataToDomain(bets: List<BetsResponseItem>): List<BetItemEntity> {
        return bets.map {
            BetItemEntity(
                it.account,
                it.createdDate,
                it.db,
                it.game,
                it.odds,
                it.operation,
                it.status,
                it.type,
                it.wager,
                it.winning
            )
        }
    }
}
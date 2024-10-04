package com.example.betschallenge.data.network.mapper.mapperBets

import com.example.betschallenge.data.entity.BetsResponseItem
import com.example.betschallenge.domain.entity.bets.BetItemEntity

interface BetsMapper {
    suspend fun betsDataToDomain(bets: List<BetsResponseItem>): List<BetItemEntity>
}
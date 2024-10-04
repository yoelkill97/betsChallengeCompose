package com.example.betschallenge.data.network.mapper.bets_detail

import com.example.betschallenge.data.entity.BetsDetailResponse
import com.example.betschallenge.domain.entity.bets_detail.BetDetailEntity

interface BetsDetailMapper {
    suspend fun betsDetailDataToDomain(betsDetail: List<BetsDetailResponse>): List<BetDetailEntity>
}
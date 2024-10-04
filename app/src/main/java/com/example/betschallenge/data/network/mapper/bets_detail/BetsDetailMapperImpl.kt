package com.example.betschallenge.data.network.mapper.bets_detail

import com.example.betschallenge.data.entity.BetsDetailResponse
import com.example.betschallenge.domain.entity.bets_detail.BetDetailEntity
import com.example.betschallenge.domain.entity.bets_detail.BetSelectionEntity
import javax.inject.Inject

class BetsDetailMapperImpl @Inject constructor() : BetsDetailMapper {
    override suspend fun betsDetailDataToDomain(betsDetail: List<BetsDetailResponse>): List<BetDetailEntity> {
        return betsDetail.map {
            BetDetailEntity(
                betId = it.betId,
                betType = it.betType,
                betStatus = it.betStatus,
                betSelections = it.betSelections.let {
                           bt-> bt.map { bti->
                               BetSelectionEntity(
                                   selectionId = bti.selectionId,
                                   selectionStatus = bti.selectionStatus,
                                   price = bti.price,
                                   eventName = bti.eventName,
                                   marketName = bti.marketName,
                                   eventId = bti.eventId,
                                   feedEventId = bti.feedEventId,
                                   eventDate = bti.eventDate,
                               )
                           }
                },
                createdDate = it.createdDate,
                cashoutValue = it.cashoutValue,
                totalWin = it.totalWin,
                totalStake = it.totalStake,
                totalOdds = it.totalOdds,
                cashoutOdds = it.cashoutOdds,
                bgSrc = it.bgSrc,
                betTypeName = it.betTypeName,
                betStatusName = it.betStatusName,
                betStarts = it.betStarts,
                betNivel = it.betNivel

            )
        }
    }

}
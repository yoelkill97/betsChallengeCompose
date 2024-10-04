package com.example.betschallenge.domain.entity.bets_detail


data class BetDetailEntity(
    val betNivel: String = "",
    val betStarts: Int = 0,
    val betStatusName: String = "",
    val betTypeName: String = "",
    val bgSrc: String = "",
    val cashoutOdds: String = "",
    val totalOdds: String = "",
    val totalStake: String = "",
    val totalWin: String = "",
    val cashoutValue: String = "",
    val createdDate: String = "",
    val betSelections: List<BetSelectionEntity> = listOf(),
    val betStatus: Int = 0,
    val betType: Int = 0,
    val betId: Long = 0L
)

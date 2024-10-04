package com.example.betschallenge.data.entity


import com.google.firebase.database.IgnoreExtraProperties
import com.google.firebase.database.PropertyName

@IgnoreExtraProperties
data class BetsDetailResponse(
    @PropertyName("BetNivel") val betNivel: String = "",
    @PropertyName("BetStarts") val betStarts: Int = 0,
    @PropertyName("BetStatusName") val betStatusName: String = "",
    @PropertyName("BetTypeName") val betTypeName: String = "",
    @PropertyName("BgSrc") val bgSrc: String = "",
    @PropertyName("CashoutOdds") val cashoutOdds: String = "",
    @PropertyName("TotalOdds") val totalOdds: String = "",
    @PropertyName("TotalStake") val totalStake: String = "",
    @PropertyName("TotalWin") val totalWin: String = "",
    @PropertyName("CashoutValue") val cashoutValue: String = "",
    @PropertyName("CreatedDate") val createdDate: String = "",
    @PropertyName("BetSelections") val betSelections: List<BetSelectionResponse> = listOf(),
    @PropertyName("BetStatus") val betStatus: Int = 0,
    @PropertyName("BetType") val betType: Int = 0,
    @PropertyName("BetId") val betId: Long = 0L
)
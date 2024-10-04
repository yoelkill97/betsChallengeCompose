package com.example.betschallenge.domain.entity.bets_detail

data class BetSelectionEntity(
    val selectionId: Long = 0L,
    val selectionStatus: Int = 0,
    val price: String = "",
    val name: String = "",
    val eventName: String = "",
    val marketName: String = "",
    val eventId: Long = 0L,
    val feedEventId: Long = 0L,
    val eventDate: String = ""
)

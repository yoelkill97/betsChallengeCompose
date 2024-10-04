package com.example.betschallenge.domain.entity.bets

data class BetItemEntity(
    val account: String,
    val createdDate: String,
    val db: Int,
    val game: String,
    val odds: Double,
    val operation: Int,
    val status: String,
    val type: String,
    val wager: Int,
    val winning: Int?
)
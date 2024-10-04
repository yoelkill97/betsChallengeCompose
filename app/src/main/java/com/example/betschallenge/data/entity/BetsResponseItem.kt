package com.example.betschallenge.data.entity

import com.google.firebase.database.IgnoreExtraProperties
import com.google.firebase.database.PropertyName
@IgnoreExtraProperties
data class BetsResponseItem(
    @PropertyName("account") val account: String = "",
    @PropertyName("created_date") val createdDate: String = "", // Si `created_date` en Firebase no coincide con `createdDate`
    @PropertyName("db") val db: Int = 0,
    @PropertyName("game") val game: String = "",
    @PropertyName("odds") val odds: Double = 0.0,
    @PropertyName("operation") val operation: Int = 0,
    @PropertyName("status") val status: String = "",
    @PropertyName("type") val type: String = "",
    @PropertyName("wager") val wager: Int = 0,
    @PropertyName("winning") val winning: Int? = null
)
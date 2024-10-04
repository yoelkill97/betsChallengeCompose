package com.example.betschallenge.data.entity


import com.google.firebase.database.IgnoreExtraProperties
import com.google.firebase.database.PropertyName

@IgnoreExtraProperties
data class BetSelectionResponse(
    @PropertyName("SelectionId") val selectionId: Long = 0L,
    @PropertyName("SelectionStatus") val selectionStatus: Int = 0,
    @PropertyName("Price") val price: String = "",
    @PropertyName("Name") val name: String = "",
    @PropertyName("EventName") val eventName: String = "",  // Campo relevante para el filtro
    @PropertyName("MarketName") val marketName: String = "",
    @PropertyName("EventId") val eventId: Long = 0L,
    @PropertyName("FeedEventId") val feedEventId: Long = 0L,
    @PropertyName("EventDate") val eventDate: String = ""
)
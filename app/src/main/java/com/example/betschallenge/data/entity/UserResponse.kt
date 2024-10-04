package com.example.betschallenge.data.entity

import com.google.firebase.database.IgnoreExtraProperties
import com.google.firebase.database.PropertyName

@IgnoreExtraProperties
data class UserResponse(
    @PropertyName("correo")  val correo: String,
    @PropertyName("name") val name: String,
)
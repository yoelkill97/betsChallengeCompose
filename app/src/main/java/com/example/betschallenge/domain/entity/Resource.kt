package com.example.betschallenge.domain.entity

sealed class Resource<out T> {
    data object Loading : Resource<Nothing>()
    data object Idle : Resource<Nothing>()
    data class Success<T>(val data: T) : Resource<T>()
    data class Error<Nothing>(val message: String) : Resource<Nothing>()
}

fun <T> Resource<T>.getErrorOrNull(): String? {
    return (this as? Resource.Error)?.message
}

fun <T> Resource<T>.getSuccessOrNull(): T? {
    return (this as? Resource.Success)?.data
}

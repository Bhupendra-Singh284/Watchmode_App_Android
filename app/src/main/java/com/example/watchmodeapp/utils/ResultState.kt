package com.example.watchmodeapp.utils

sealed class ResultState<out T> {
    object Loading : ResultState<Nothing>()
    data class Success<T>(val data: T) : ResultState<T>()
    data class Failure(val error: String) : ResultState<Nothing>()
}
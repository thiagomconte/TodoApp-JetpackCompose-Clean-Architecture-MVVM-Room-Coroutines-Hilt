package com.example.domain.utils

sealed class TodoState<out T> {
    object Loading : TodoState<Nothing>()
    object Empty : TodoState<Nothing>()
    data class Error(val error: String) : TodoState<Nothing>()
    data class Success<out T>(val data: T) : TodoState<T>()
}

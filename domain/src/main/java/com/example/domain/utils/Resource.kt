package com.example.domain.utils

sealed class Resource<out T> {
    data class Error(val error: String) : Resource<Nothing>()
    data class Success<out T>(val data: T) : Resource<T>()
}

package com.example.istestproject.di

sealed class RequestStates<T>(
    val data: T? = null,
    val message: String? = null
) {
    class Success<T>(data: T) : RequestStates<T>(data)
    class Error<T>(message: String, data: T? = null) : RequestStates<T>(data, message)
    class Loading<T> : RequestStates<T>()
}
package com.zogik.cinema.utils

sealed class State<T>(
    var data: T? = null,
    var message: String? = null
) {
    class Loading<T> : State<T>()
    class Success<T>(data: T) : State<T>(data)
    class Error<T>(message: String, data: T? = null) : State<T>(data, message)
}

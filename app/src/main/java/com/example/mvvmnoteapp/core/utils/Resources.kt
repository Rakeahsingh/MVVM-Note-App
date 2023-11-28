package com.example.mvvmnoteapp.core.utils

sealed class Resources<T>(val data: T? = null, val message: String? = null){

    class Success<T>(data: T): Resources<T>(data)
    class Error<T>(data: T? = null, message: String): Resources<T>(data, message)
    class Loading<T>(data: T? = null): Resources<T>(data)

}

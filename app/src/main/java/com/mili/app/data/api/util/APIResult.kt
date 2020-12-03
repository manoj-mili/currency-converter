package com.mili.app.data.api.util

sealed class APIResult<out T> {
    data class Init(val nothing: Nothing? = null) : APIResult<Nothing>()
    data class Loading(val nothing: Nothing? = null) : APIResult<Nothing>()
    data class Success<out T>(val data: T? = null) : APIResult<T>()
    data class Failed(val exception: kotlin.Exception? = null, val message: String? = null) :
        APIResult<Nothing>()
}
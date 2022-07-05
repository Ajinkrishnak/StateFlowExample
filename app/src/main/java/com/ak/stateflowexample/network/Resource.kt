package com.ak.stateflowexample.network

import okhttp3.ResponseBody

sealed class Resource<out T> {

    data class Success<out T>(val value: T) : Resource<T>()

    data class Failure(
        val isNetworkError: Boolean,
        val errorCode: Int?,
        val errorBody: ResponseBody?,
        val errorMessage: String?
    ) : Resource<Nothing>()

    object Loading : Resource<Nothing>()

}
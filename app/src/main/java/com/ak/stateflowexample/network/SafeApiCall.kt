package com.ak.stateflowexample.network

import retrofit2.HttpException


interface SafeApiCall {

    suspend fun <T> safeApiCall(
        apiCall: suspend () -> T
    ): Resource<T> {
        return try {
            Resource.Success(apiCall.invoke())
        } catch (throwable: Throwable) {
            when (throwable) {
                is HttpException -> {
                    Resource.Failure(
                        false,
                        throwable.code(),
                        throwable.response()?.errorBody(),
                        throwable.response()?.let { ResponseCodeManager.checkRetrofitApiResponse(it) })
                }
                else -> {
                    Resource.Failure(true, null, null, "")
                }
            }
        }
    }


}
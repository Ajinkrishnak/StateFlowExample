package com.ak.stateflowexample.network
import com.ak.stateflowexample.utils.Constants.CONVERSION_FAILURE
import com.ak.stateflowexample.utils.Constants.NETWORK_FAILURE
import retrofit2.HttpException
import java.io.IOException

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
                        throwable.response()
                            ?.let { ResponseCodeManager.checkRetrofitApiResponse(it) })
                }
                is IOException->{
                    Resource.Failure(true, null, null, NETWORK_FAILURE)
                }
                else -> {
                    Resource.Failure(false, null, null, CONVERSION_FAILURE)
                }
            }
        }
    }


}
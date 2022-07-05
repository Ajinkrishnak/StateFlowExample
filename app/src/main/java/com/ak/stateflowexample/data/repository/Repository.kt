package com.ak.stateflowexample.data.repository

import com.ak.stateflowexample.network.ApiService
import com.ak.stateflowexample.network.SafeApiCall
import javax.inject.Inject

class Repository @Inject constructor(private val apiService: ApiService) : SafeApiCall {

    suspend fun getAlbums() = safeApiCall { apiService.getAlbums() }

}
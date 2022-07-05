package com.ak.stateflowexample.network

import com.ak.stateflowexample.data.model.AlbumsItem
import com.ak.stateflowexample.utils.EndPoints
import retrofit2.http.GET

interface ApiService {

    //get albums
    @GET(EndPoints.ALBUMS)
    suspend fun getAlbums(): List<AlbumsItem>


}
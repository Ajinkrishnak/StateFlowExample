package com.ak.stateflowexample.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ak.stateflowexample.data.model.AlbumsItem
import com.ak.stateflowexample.data.repository.Repository
import com.ak.stateflowexample.network.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AlbumsViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    private val mAlbumsResponse = MutableStateFlow<Resource<List<AlbumsItem>>>(Resource.Loading)
    val albumsResponse: StateFlow<Resource<List<AlbumsItem>>> get() = mAlbumsResponse

    init {
        getNft()
    }

    private fun getNft() = viewModelScope.launch {

        mAlbumsResponse.emit(Resource.Loading)
        val albums =   repository.getAlbums()

        if(albums is Resource.Success){
            mAlbumsResponse.emit(Resource.Success(albums.value))
        }else{
            Resource.Failure(false, null, null)
        }

    }

}
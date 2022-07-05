package com.ak.stateflowexample.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.ak.stateflowexample.databinding.ActivityMainBinding
import com.ak.stateflowexample.network.Resource
import com.ak.stateflowexample.ui.adapters.AlbumsAdapter
import com.ak.stateflowexample.ui.viewmodels.AlbumsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val albumsViewModel: AlbumsViewModel by viewModels()
    private var binding: ActivityMainBinding? = null
    private val albumsAdapter by lazy { AlbumsAdapter() }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        init()
    }

    private fun init() {
        setUpAdapter()
        setDataFromApi()
    }

    private fun setUpAdapter() {

        binding?.recyclerView?.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            setHasFixedSize(true)
            adapter = albumsAdapter
        }
    }

    private fun setDataFromApi() {

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                albumsViewModel.albumsResponse.collect { result ->
                    when (result) {
                        Resource.Loading -> {
                            binding?.progressCircular?.visibility = View.VISIBLE
                        }
                        is Resource.Failure -> {
                            binding?.progressCircular?.visibility = View.GONE
//                            if (result.isNetworkError){
//
//                            }else{
//
//                            }
                            Toast.makeText(
                                this@MainActivity,
                                result.errorMessage,
                                Toast.LENGTH_SHORT
                            ).show()

                        }
                        is Resource.Success -> {
                            binding?.progressCircular?.visibility = View.GONE
                            albumsAdapter.submitList(result.value)
                        }
                    }

                }
            }
        }
    }

}
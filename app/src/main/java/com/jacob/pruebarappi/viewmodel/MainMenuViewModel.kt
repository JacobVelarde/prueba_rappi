package com.jacob.pruebarappi.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.jacob.pruebarappi.BuildConfig
import com.jacob.pruebarappi.base.BaseViewModel
import com.jacob.pruebarappi.models.TopRatedRequest
import com.jacob.pruebarappi.models.TopRatedResponse
import com.jacob.pruebarappi.models.UpComingRequest
import com.jacob.pruebarappi.models.UpComingResponse
import com.jacob.pruebarappi.repositories.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainMenuViewModel
@Inject constructor(private val movieRepository: MovieRepository): BaseViewModel() {

    fun getUpComing(): LiveData<UpComingResponse>{
        val upComingRequest = UpComingRequest(
            BuildConfig.API_KEY_THE_MOVIE_DB,
            "es-MX",
            1)

        val res = MutableLiveData<UpComingResponse>()
        executeService(loader = true) {
            val upComingResponse = movieRepository.upComing(upComingRequest)
            upComingResponse.let {
                res.postValue(it)
            }
        }

        return res
    }

    fun getTrend(): LiveData<TopRatedResponse>{
        val topRatedRequest = TopRatedRequest(
            BuildConfig.API_KEY_THE_MOVIE_DB,
            "es-MX",
            1)

        val res = MutableLiveData<TopRatedResponse>()
        executeService(loader = true) {
            val topRatedRequest = movieRepository.topRated(topRatedRequest)
            topRatedRequest.let {
                res.postValue(it)
            }
        }

        return res
    }
}
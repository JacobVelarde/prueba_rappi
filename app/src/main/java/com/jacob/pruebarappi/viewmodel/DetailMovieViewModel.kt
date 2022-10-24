package com.jacob.pruebarappi.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.jacob.pruebarappi.BuildConfig
import com.jacob.pruebarappi.base.BaseViewModel
import com.jacob.pruebarappi.models.VideoRequest
import com.jacob.pruebarappi.models.VideoResponse
import com.jacob.pruebarappi.repositories.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailMovieViewModel
@Inject constructor(private val movieRepository: MovieRepository): BaseViewModel(){

    fun getVideos(movieId: Int): LiveData<VideoResponse>{
        var videoRequest = VideoRequest(
            BuildConfig.API_KEY_THE_MOVIE_DB,
            "en-US",
            movieId
        )
        val res = MutableLiveData<VideoResponse>()
        executeService(loader = true) {
            val videoResponse = movieRepository.getVideos(videoRequest)
            videoResponse.let {
                res.postValue(it)
            }
        }

        return res
    }
}
package com.jacob.pruebarappi.repositories

import com.jacob.pruebarappi.models.*
import com.jacob.pruebarappi.network.check
import com.jacob.pruebarappi.network.services.ApiMovieDB

class MovieRepository(private val apiMovieDB: ApiMovieDB) {

    suspend fun upComing(upComingRequest: UpComingRequest) : UpComingResponse?{
        val response = apiMovieDB.upComing(
            upComingRequest.apiKey,
            upComingRequest.language,
            upComingRequest.page)

        return response.check()
    }

    suspend fun topRated(topRatedRequest: TopRatedRequest) : TopRatedResponse?{
        val response = apiMovieDB.topRated(
            topRatedRequest.apiKey,
            topRatedRequest.language,
            topRatedRequest.page)

        return response.check()
    }

    suspend fun getVideos(videoRequest: VideoRequest): VideoResponse?{
        val response = apiMovieDB.getVideos(videoRequest.movieId, videoRequest.apiKey, videoRequest.language)
        return response.check()
    }
}
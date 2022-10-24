package com.jacob.pruebarappi.network.services

import com.jacob.pruebarappi.BuildConfig
import com.jacob.pruebarappi.models.TopRatedResponse
import com.jacob.pruebarappi.models.UpComingResponse
import com.jacob.pruebarappi.models.VideoResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiMovieDB {

    @GET(BuildConfig.MOVIE_UP_COMING)
    suspend fun upComing(
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        @Query("page") page: Int): Response<UpComingResponse>

    @GET(BuildConfig.MOVIE_TOP_RATED)
    suspend fun topRated(
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        @Query("page") page: Int): Response<TopRatedResponse>

    @GET(BuildConfig.MOVIE_VIDEOS)
    suspend fun getVideos(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
    ): Response<VideoResponse>

}
package com.jacob.pruebarappi.di

import com.google.gson.GsonBuilder
import com.jacob.pruebarappi.BuildConfig
import com.jacob.pruebarappi.network.services.ApiMovieDB
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetWorkModule {

    @Provides
    @Singleton
    fun provideCustomRetrofit(retrofit: Retrofit.Builder?) : Retrofit? {
        return retrofit?.build()
    }

    @Provides
    @Singleton
    fun provideRetrofitBuilder(
        okHttpClient: OkHttpClient?,
        converterFactory: Converter.Factory?
    ): Retrofit.Builder? {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(converterFactory)
    }

    @Provides
    @Singleton
    fun provideHttpClient(): OkHttpClient? {
        return  OkHttpClient().newBuilder()
            .readTimeout(180, TimeUnit.SECONDS)
            .connectTimeout(180, TimeUnit.SECONDS)
            .build()

    }


    @Provides
    @Singleton
    fun provideConverterFactory(): Converter.Factory? {
        return GsonConverterFactory.create(
            GsonBuilder().serializeNulls().create()
        )
    }


    @Provides
    @Singleton
    fun provideApiMovieDB(retrofit: Retrofit?) = retrofit?.create(ApiMovieDB::class.java)
}
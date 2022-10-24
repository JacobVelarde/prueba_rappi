package com.jacob.pruebarappi.di

import com.jacob.pruebarappi.network.services.ApiMovieDB
import com.jacob.pruebarappi.repositories.MovieRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
class MainMenuModule {

    @Provides
    @ViewModelScoped
    fun provideMovieRepository(apiMovieDB: ApiMovieDB?) = MovieRepository(apiMovieDB!!)

}
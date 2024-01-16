package com.hacine.mohamed.hakim.flixflex.di


import com.hacine.mohamed.hakim.flixflex.data.repository.MoviesRepository
import com.hacine.mohamed.hakim.flixflex.data.repository.SearchRepository
import com.hacine.mohamed.hakim.yassirtestmoviedb_hacinemed.data.network.Api
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class RepoModule {

    @Provides
    fun provideMoviesRepository(api: Api) : MoviesRepository {
        return MoviesRepository(api)
    }
    @Singleton
    @Provides
    fun provideSearchRepository(api: Api) = SearchRepository(api = api)

}
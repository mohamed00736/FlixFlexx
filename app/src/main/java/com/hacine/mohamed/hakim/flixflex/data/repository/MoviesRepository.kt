package com.hacine.mohamed.hakim.flixflex.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.hacine.mohamed.hakim.yassirtestmoviedb_hacinemed.data.network.Api
import com.hacine.mohamed.hakim.flixflex.data.network.SafeApiCall
import javax.inject.Inject

class MoviesRepository @Inject constructor(private val api: Api, ) : SafeApiCall {



    suspend fun getMovieById(movieid :String ) = safeApiCall {
        api.getMovieById(movieid)
    }

    suspend fun getSeriesById(serie_id :String ) = safeApiCall {
        api.getSeriesById(serie_id)
    }
    suspend fun getTrendingMovies() = safeApiCall {
        api.getTrendingMovies()
    }

    suspend fun getTrendingSeries() = safeApiCall {
        api.getTrendingSeries()
    }

    suspend fun getMovieVideos(movie_id: String) = safeApiCall {
        api.getMovieVideos(movie_id)
    }
    suspend fun getSeriesVideos(serie_id: String) = safeApiCall {
        api.getSeriesVideos(serie_id)
    }

    fun getMoviesPaged() = Pager(
        config = PagingConfig(
            pageSize = 10,
            prefetchDistance = 10 * 1,
            initialLoadSize = 10,
            enablePlaceholders = false
        ),
        pagingSourceFactory = {
            MoviesPagingSource(api)
        }

    ).flow

    fun getSeriesPaged() = Pager(
        config = PagingConfig(
            pageSize = 10,
            prefetchDistance = 10 * 1,
            initialLoadSize = 10,
            enablePlaceholders = false
        ),
        pagingSourceFactory = {
            SeriesPagingSource(api)
        }

    ).flow


}
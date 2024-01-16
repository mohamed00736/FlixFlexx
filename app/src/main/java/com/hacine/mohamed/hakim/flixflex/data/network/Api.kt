package com.hacine.mohamed.hakim.yassirtestmoviedb_hacinemed.data.network


import com.hacine.mohamed.hakim.flixflex.data.network.Constants.Companion.API_KEY
import com.hacine.mohamed.hakim.flixflex.data.models.Movie
import com.hacine.mohamed.hakim.flixflex.data.models.MovieList
import com.hacine.mohamed.hakim.flixflex.data.models.MediaVideosList
import com.hacine.mohamed.hakim.flixflex.data.models.MultiSearchResponse
import com.hacine.mohamed.hakim.flixflex.data.models.Tv
import com.hacine.mohamed.hakim.flixflex.data.models.TvList
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface Api {




    @GET("3/discover/movie")
    suspend fun getDiscoverMovies(
        @Query("page") page: Int,
        @Query("api_key") api_key: String = API_KEY,
    ): MovieList

    @GET("3/discover/tv")
    suspend fun getDiscoverSeries(
        @Query("page") page: Int,
        @Query("api_key") api_key: String = API_KEY,
    ): TvList

    @GET("3/movie/{movie_id}")
    suspend fun getMovieById(
        @Path("movie_id") movie_id: String, @Query("api_key") api_key: String = API_KEY
    ): Movie

    @GET("3/tv/{series_id}")
    suspend fun getSeriesById(
        @Path("series_id") series_id: String, @Query("api_key") api_key: String = API_KEY
    ): Tv


    @GET("3/trending/movie/day")
    suspend fun getTrendingMovies(@Query("api_key") api_key: String = API_KEY): MovieList

    @GET("3/trending/tv/day")
    suspend fun getTrendingSeries(@Query("api_key") api_key: String = API_KEY): TvList

    @GET("3/search/multi")
    suspend fun multiSearch(
        @Query("query") searchParams: String,
        @Query("page") page: Int = 1,
        @Query("include_adult") includeAdult: Boolean = true,
        @Query("api_key") apiKey: String = API_KEY,
        @Query("language") language: String = "en"
    ): MultiSearchResponse


    @GET("3/movie/{movie_id}/videos")
    suspend fun getMovieVideos(
        @Path("movie_id") movie_id: String,
        @Query("api_key") api_key: String = API_KEY): MediaVideosList

    @GET("3/tv/{series_id}/videos")
    suspend fun getSeriesVideos(
        @Path("series_id") series_id: String,
        @Query("api_key") api_key: String = API_KEY): MediaVideosList
}
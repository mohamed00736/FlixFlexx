package com.hacine.mohamed.hakim.flixflex.data.repository

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.hacine.mohamed.hakim.flixflex.data.models.Movie
import com.hacine.mohamed.hakim.yassirtestmoviedb_hacinemed.data.network.Api
import retrofit2.HttpException
import java.io.IOException
import java.net.UnknownHostException

class MoviesPagingSource (
    private val api: Api
): PagingSource<Int, Movie>() {
    private val STARTING_PAGE_INDEX = 1


    override fun getRefreshKey(state: PagingState<Int, Movie>) =
        state.anchorPosition?.let { anchorPosition ->
            val anchorPageIndex = state.pages.indexOf(state.closestPageToPosition(anchorPosition))
            state.pages.getOrNull(anchorPageIndex + 1)?.prevKey ?: state.pages.getOrNull(
                anchorPageIndex - 1
            )?.nextKey
        }



    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        val position = params.key ?: STARTING_PAGE_INDEX
        return try {

            val moviesResult = api.getDiscoverMovies(
                page = position
            )
            Log.d("getDiscoverMovies2", "load: $position, results: ${moviesResult.results.size}")



            LoadResult.Page(
                data = moviesResult.results,
                prevKey = if (position == STARTING_PAGE_INDEX) null else position - 1,
                nextKey = if (moviesResult.results.isEmpty()) null else position + 1
            )
        } catch (exception: IOException) {
            if (exception is UnknownHostException)
                LoadResult.Error(UnknownHostException())
            else LoadResult.Error(Exception())
        } catch (exception: HttpException) {
            LoadResult.Error(Exception())
        }
    }
    }

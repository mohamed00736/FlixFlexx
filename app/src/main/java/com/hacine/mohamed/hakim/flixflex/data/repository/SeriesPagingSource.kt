package com.hacine.mohamed.hakim.flixflex.data.repository

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.hacine.mohamed.hakim.flixflex.data.models.Tv
import com.hacine.mohamed.hakim.yassirtestmoviedb_hacinemed.data.network.Api
import retrofit2.HttpException
import java.io.IOException
import java.net.UnknownHostException

class SeriesPagingSource (
    private val api: Api
): PagingSource<Int, Tv>() {
    private val STARTING_PAGE_INDEX = 1


    override fun getRefreshKey(state: PagingState<Int, Tv>) =
        state.anchorPosition?.let { anchorPosition ->
            val anchorPageIndex = state.pages.indexOf(state.closestPageToPosition(anchorPosition))
            state.pages.getOrNull(anchorPageIndex + 1)?.prevKey ?: state.pages.getOrNull(
                anchorPageIndex - 1
            )?.nextKey
        }



    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Tv> {
        val position = params.key ?: STARTING_PAGE_INDEX
        return try {

            val seriesResult = api.getDiscoverSeries(
                page = position
            )
            Log.d("getDiscoverSeries2", "load: $position, results: ${seriesResult.results.size}")



            LoadResult.Page(
                data = seriesResult.results,
                prevKey = if (position == STARTING_PAGE_INDEX) null else position - 1,
                nextKey = if (seriesResult.results.isEmpty()) null else position + 1
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

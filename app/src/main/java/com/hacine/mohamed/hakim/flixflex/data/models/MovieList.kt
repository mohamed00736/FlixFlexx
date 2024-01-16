package com.hacine.mohamed.hakim.flixflex.data.models

import com.google.gson.annotations.SerializedName

data class MovieList(

    @SerializedName("page") var page: Int? = null,
    @SerializedName("results") var results: ArrayList<Movie> = arrayListOf(),
    @SerializedName("total_pages") var totalPages: Int? = null,
    @SerializedName("total_results") var totalResults: Int? = null
)

data class TvList(

    @SerializedName("page") var page: Int? = null,
    @SerializedName("results") var results: ArrayList<Tv> = arrayListOf(),
    @SerializedName("total_pages") var totalPages: Int? = null,
    @SerializedName("total_results") var totalResults: Int? = null
)
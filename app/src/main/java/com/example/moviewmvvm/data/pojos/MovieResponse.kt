package com.example.moviewmvvm.data.pojos


import com.google.gson.annotations.SerializedName

data class MovieResponse(
    @SerializedName("page")
    val page: Int,
    @SerializedName("results")
    val movieList: List<Movie>
)
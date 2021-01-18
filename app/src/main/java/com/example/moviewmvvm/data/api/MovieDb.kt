package com.example.moviewmvvm.data.api

import com.example.moviewmvvm.data.pojos.MovieDetails
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface MovieDb {

    @GET("movie/{movie_id}")
    fun getMovieDetails(@Path("movie_id") id:Int) : Single<MovieDetails>

}
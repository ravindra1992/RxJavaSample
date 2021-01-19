package com.example.moviewmvvm.data.api

import com.example.moviewmvvm.data.pojos.MovieDetails
import com.example.moviewmvvm.data.pojos.MovieResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieDb {

    @GET("movie/{movie_id}")
    fun getMovieDetails(@Path("movie_id") id:Int) : Single<MovieDetails>


    @GET("movie/popular")
    fun getPopularMovie(@Query("page") page:Int) :Single<MovieResponse>

}
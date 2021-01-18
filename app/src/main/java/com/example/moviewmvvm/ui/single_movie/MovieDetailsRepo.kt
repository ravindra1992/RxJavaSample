package com.example.moviewmvvm.ui.single_movie

import androidx.lifecycle.LiveData
import com.example.moviewmvvm.data.api.MovieDb
import com.example.moviewmvvm.data.api.NetworkState
import com.example.moviewmvvm.data.pojos.MovieDetails
import com.example.moviewmvvm.data.repository.MoviewDetailsNetworkSource
import io.reactivex.disposables.CompositeDisposable

class MovieDetailsRepo(private val apiService: MovieDb) {

     lateinit var movieDetailsNetworkSource: MoviewDetailsNetworkSource

    fun fetchSingleMovieDetails(compositeDisposable: CompositeDisposable, moviewId:Int) : LiveData<MovieDetails>{


        movieDetailsNetworkSource= MoviewDetailsNetworkSource(apiService, compositeDisposable)
        movieDetailsNetworkSource.fetchMovieDetails(moviewId)

        return movieDetailsNetworkSource.movieDetails
    }


    fun getMovieDetailNetworkState():LiveData<NetworkState>{

        return movieDetailsNetworkSource.networkState
    }




}
package com.example.moviewmvvm.ui.single_movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.moviewmvvm.data.api.NetworkState
import com.example.moviewmvvm.data.pojos.MovieDetails
import io.reactivex.disposables.CompositeDisposable

class SingleMovieViewModel(private val movieDetailsRepo: MovieDetailsRepo, movieid: Int) :ViewModel() {

    private val compositeDisposable= CompositeDisposable()

    val movieDetails: LiveData<MovieDetails> by lazy {

        movieDetailsRepo.fetchSingleMovieDetails(compositeDisposable, movieid)
    }

    val networkState: LiveData<NetworkState> by lazy {

        movieDetailsRepo.getMovieDetailNetworkState()
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }



}
package com.example.moviewmvvm.data.repository

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.example.moviewmvvm.data.api.MovieDb
import com.example.moviewmvvm.data.pojos.Movie
import io.reactivex.disposables.CompositeDisposable


class MovieDataSourceFactory(private val apiService: MovieDb , private val compositeDisposable: CompositeDisposable) : DataSource.Factory<Int, Movie>()  {

    val moviesLiveDataSource = MutableLiveData<MovieDataSource>()

    override fun create(): DataSource<Int, Movie> {

        val movieDataSource = MovieDataSource(apiService, compositeDisposable)
        moviesLiveDataSource.postValue(movieDataSource)
        return  movieDataSource

    }
}
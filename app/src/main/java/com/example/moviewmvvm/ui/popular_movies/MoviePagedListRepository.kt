package com.example.moviewmvvm.ui.popular_movies

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.moviewmvvm.data.api.MovieDb
import com.example.moviewmvvm.data.api.NetworkState
import com.example.moviewmvvm.data.api.POST_PER_PAGE
import com.example.moviewmvvm.data.pojos.Movie
import com.example.moviewmvvm.data.repository.MovieDataSource
import com.example.moviewmvvm.data.repository.MovieDataSourceFactory
import io.reactivex.disposables.CompositeDisposable

class MoviePagedListRepository(private val apiService: MovieDb) {

    lateinit var moviePagedList: LiveData<PagedList<Movie>>
    lateinit var movieDataSourceFactory: MovieDataSourceFactory


    fun fetchLiveMoviePagedList(compositeDisposable: CompositeDisposable) : LiveData<PagedList<Movie>>{

        movieDataSourceFactory= MovieDataSourceFactory(apiService, compositeDisposable)
        val config= PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPageSize(POST_PER_PAGE)
            .build()

        moviePagedList= LivePagedListBuilder(movieDataSourceFactory, config).build()
        return moviePagedList
    }


    fun getNetworkState() : LiveData<NetworkState>{
        return  Transformations.switchMap<MovieDataSource, NetworkState>(
            movieDataSourceFactory.moviesLiveDataSource, MovieDataSource::networkState
        )

    }
}
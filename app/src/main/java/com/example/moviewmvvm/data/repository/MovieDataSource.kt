package com.example.moviewmvvm.data.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.example.moviewmvvm.data.api.FIRST_PAGE
import com.example.moviewmvvm.data.api.MovieDb
import com.example.moviewmvvm.data.api.NetworkState
import com.example.moviewmvvm.data.pojos.Movie
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MovieDataSource(private val apiService: MovieDb, private val compositeDisposable: CompositeDisposable) :PageKeyedDataSource<Int, Movie>() {
    private var page= FIRST_PAGE
    val networkState: MutableLiveData<NetworkState> = MutableLiveData()

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, Movie>) {

       networkState.postValue(NetworkState.LOADING)

        compositeDisposable.add(apiService.getPopularMovie(page)
            .subscribeOn(Schedulers.io()).subscribe({

                callback.onResult(it.movieList, null, page+1)
                networkState.postValue(NetworkState.LOADED)


            },
                {
                    networkState.postValue(NetworkState.ERROR)
                    Log.e("MovieDataSource", it.message.toString())

                }

            )
        )
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Movie>) {

        networkState.postValue(NetworkState.LOADING)

        compositeDisposable.add(apiService.getPopularMovie(params.key)
            .subscribeOn(Schedulers.io()).subscribe({

                 callback.onResult(it.movieList, params.key+1)
                 networkState.postValue(NetworkState.LOADED)


            },
                {
                    networkState.postValue(NetworkState.ERROR)
                    Log.e("MovieDataSource", it.message.toString())

                }

            )
        )

    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Movie>) {

    }
}
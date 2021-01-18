package com.example.moviewmvvm.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.moviewmvvm.data.api.MovieDb
import com.example.moviewmvvm.data.api.NetworkState
import com.example.moviewmvvm.data.pojos.MovieDetails
import io.reactivex.Scheduler
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.internal.disposables.ArrayCompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.lang.Exception

class MoviewDetailsNetworkSource(private val apiService: MovieDb , private val compositeDisposable: CompositeDisposable) {

    private val _networkState= MutableLiveData<NetworkState>()

    val networkState: LiveData<NetworkState>
    get() = _networkState

    private val _movieDataResponse= MutableLiveData<MovieDetails>()
    val movieDetails: LiveData<MovieDetails>
    get() = _movieDataResponse


    fun fetchMovieDetails(movieid:Int){

        _networkState.postValue(NetworkState.LOADING)
        try{


          compositeDisposable.add(apiService.getMovieDetails(movieid)
              .subscribeOn(Schedulers.io()).subscribe({
                  _movieDataResponse.postValue(it)
                  _networkState.postValue(NetworkState.LOADED)


              },
                  {
                      _networkState.postValue(NetworkState.ERROR)
                      Log.e("MovieDetailsNetwork", it.message.toString())

                  }

              )
          )


        }catch (e:Exception){
            Log.e("MovieDetailsNetwork", e.message.toString())
        }

    }


}
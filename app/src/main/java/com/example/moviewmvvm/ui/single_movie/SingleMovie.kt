package com.example.moviewmvvm.ui.single_movie

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.example.moviewmvvm.R
import com.example.moviewmvvm.data.api.MovieDb
import com.example.moviewmvvm.data.api.MovieDbClient
import com.example.moviewmvvm.data.api.NetworkState
import com.example.moviewmvvm.data.api.POSTER_BASE_URL
import com.example.moviewmvvm.data.pojos.MovieDetails
import kotlinx.android.synthetic.main.activity_single_movie.*
import java.text.NumberFormat
import java.util.*

@Suppress("UNCHECKED_CAST")
class SingleMovie : AppCompatActivity() {

    private lateinit var viewModel: SingleMovieViewModel
    private lateinit var movieRepository: MovieDetailsRepo

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_single_movie)
        val movieId:Int= intent.getIntExtra("id", 1)
        val apiService: MovieDb= MovieDbClient.getClient()
        movieRepository=
            MovieDetailsRepo(apiService)
        viewModel=getViewModel(movieId)
        viewModel.movieDetails.observe(this, androidx.lifecycle.Observer {
            bindUI(it)
        })

        viewModel.networkState.observe(this, androidx.lifecycle.Observer {
            progress_bar.visibility=if (it== NetworkState.LOADING) View.VISIBLE else View.GONE
            text_error.visibility=if(it== NetworkState.ERROR) View.VISIBLE else View.GONE
        })

    }

    private fun bindUI(it: MovieDetails?) {

        movie_title.text= it!!.title
        movie_overview.text=it.overview
        movie_ratings.text=it.rating.toString()
        movie_release_date.text=it.releaseDate
        movie_runtime.text=it.runtime.toString() + " minutes"
        movie_tagline.text=it.tagline.toString()

        val formatCurrency= NumberFormat.getCurrencyInstance(Locale.US)
        movie_budget.text= formatCurrency.format(it.revenue)
        movie_revenue.text= formatCurrency.format(it.revenue)

        val moviePosterUrl= POSTER_BASE_URL+ it.posterPath
        Glide.with(this)
            .load(moviePosterUrl)
            .into(poster_image)

    }


    private fun getViewModel(movieID:Int): SingleMovieViewModel {

        return ViewModelProviders.of(this, object :ViewModelProvider.Factory{
          override  fun <T:ViewModel?> create(modelclass:Class<T>):T{
                return SingleMovieViewModel(
                    movieRepository,
                    movieID
                ) as T
            }

        })[SingleMovieViewModel::class.java]

    }
}
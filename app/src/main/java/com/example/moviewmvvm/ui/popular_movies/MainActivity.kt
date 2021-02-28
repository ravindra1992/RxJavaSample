package com.example.moviewmvvm.ui.popular_movies

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.Factory
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.example.moviewmvvm.R
import com.example.moviewmvvm.data.api.MovieDb
import com.example.moviewmvvm.data.api.MovieDbClient
import com.example.moviewmvvm.data.api.NetworkState
import com.example.moviewmvvm.ui.single_movie.SingleMovie
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainActivityViewModel
    lateinit var moviePagedListRepository: MoviePagedListRepository
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



         // add space for test git actions
   /*     btn.setOnClickListener{

            val intent= Intent(this, SingleMovie::class.java)
            intent.putExtra("id", 675327)
            this.startActivity(intent)
        }*/

        val apiService: MovieDb = MovieDbClient.getClient()
        moviePagedListRepository= MoviePagedListRepository(apiService)
        viewModel = getViewModel()
        val movieAdapter= PopularMoviePageListAdapter(this)
        val gridLayoutManager = GridLayoutManager(this, 3)


        gridLayoutManager.spanSizeLookup= object :GridLayoutManager.SpanSizeLookup(){

            override fun getSpanSize(position: Int): Int {
                val viewType= movieAdapter.getItemViewType(position)
                if(viewType==movieAdapter.MOVIE_VIEW_TYPE) return 1 else return 3
            }
        };
        recycler_movie_list.layoutManager= gridLayoutManager
        recycler_movie_list.setHasFixedSize(true)
        recycler_movie_list.adapter=movieAdapter

        viewModel.moviePagedList.observe(this, Observer {
            movieAdapter.submitList(it)
        })

        viewModel.networkState.observe(this, Observer {
            progress_bar_popular.visibility=if(viewModel.listIsEmpty() && it == NetworkState.LOADING) View.VISIBLE else View.GONE
            text_error_popular.visibility=if(viewModel.listIsEmpty() && it== NetworkState.ERROR) View.VISIBLE else View.GONE

            if(!viewModel.listIsEmpty()){
                movieAdapter.setNetworkState(it)
            }
        })

    }

    fun getViewModel(): MainActivityViewModel{
        return ViewModelProviders.of(this, object: ViewModelProvider.Factory{
            override fun <T : ViewModel?> create(modelclass: Class<T>):T{
                return MainActivityViewModel(moviePagedListRepository) as T
            }
        })[MainActivityViewModel::class.java]
    }
}
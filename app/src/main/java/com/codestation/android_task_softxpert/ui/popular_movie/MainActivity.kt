package com.codestation.android_task_softxpert.ui.popular_movie

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.codestation.android_task_softxpert.R
import com.codestation.android_task_softxpert.data.network.api.TheMovieDBClient
import com.codestation.android_task_softxpert.data.network.api.TheMovieDBInterface
import com.codestation.android_task_softxpert.data.network.repository.NetworkState
import com.codestation.android_task_softxpert.ui.search_movies.SearchActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {


    private lateinit var viewModel: MainActivityViewModel


    lateinit var movieRepository: MoviePageListRepository

    private lateinit var imageSearch:ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        imageSearch = findViewById(R.id.image_search_main)
        imageSearch.setOnClickListener {
            Intent(this,SearchActivity::class.java).also {
                startActivity(it)
            }
        }

        val apiService: TheMovieDBInterface = TheMovieDBClient.getClient()

        movieRepository =  MoviePageListRepository(apiService)/**Done**/

        viewModel = getViewModel()

        val movieAdapter = PopularMoviePagedListAdapter(this)

        val gridLayoutManager = GridLayoutManager(this, 2)

        gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                val viewType = movieAdapter.getItemViewType(position)
                if (viewType == movieAdapter.MOVIE_VIEW_TYPE) return  1
                else return 3
            }
        }

        rv_movie_list.layoutManager = gridLayoutManager
        rv_movie_list.setHasFixedSize(true)
        rv_movie_list.adapter = movieAdapter

        viewModel.moviePagedListed.observe(this, Observer {//moviePagedList
            movieAdapter.submitList(it)
        })

        viewModel.networkState.observe(this, Observer {
            progress_bar_popular.visibility = if (viewModel.listsEmpty() && it == NetworkState.LOADING) View.VISIBLE else View.GONE

            if (!viewModel.listsEmpty()) {
                movieAdapter.setNetworkState(it)
            }
        })


    }

    private fun getViewModel(): MainActivityViewModel {
        return ViewModelProviders.of(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return MainActivityViewModel(movieRepository) as T
            }
        })[MainActivityViewModel::class.java]
    }
}
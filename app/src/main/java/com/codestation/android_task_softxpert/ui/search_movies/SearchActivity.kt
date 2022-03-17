package com.codestation.android_task_softxpert.ui.search_movies

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.codestation.android_task_softxpert.R
import com.codestation.android_task_softxpert.model.Movie
import com.codestation.android_task_softxpert.model.MovieResponse


class SearchActivity : AppCompatActivity() {

    lateinit var viewModel:  SearchActivityViewModel
    lateinit var movieSearchAdapter: MovieSearchAdapter

    lateinit var movieList : ArrayList<Movie>
    private lateinit var inputSearch: EditText
    lateinit var imageBackSearch: ImageView
    lateinit var rv_movie_search_list: RecyclerView
    lateinit var progress_bar_search: ProgressBar


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        imageBackSearch = findViewById(R.id.image_back_search)
        movieSearchAdapter = MovieSearchAdapter(this)
        progress_bar_search = findViewById(R.id.progress_bar_search)
        rv_movie_search_list = findViewById(R.id.rv_movie_search_list)
        inputSearch = findViewById(R.id.input_search)
        
        initRecyclerView()
        initSearchBox()
       
        imageBackSearch.setOnClickListener { finish() }

    }

    fun initRecyclerView(){
        rv_movie_search_list.apply {
            layoutManager = LinearLayoutManager(this@SearchActivity)
            val decoration  = DividerItemDecoration(applicationContext, StaggeredGridLayoutManager.VERTICAL)
            addItemDecoration(decoration)
            movieSearchAdapter = MovieSearchAdapter(this@SearchActivity)
            adapter = movieSearchAdapter
        }
    }


    fun initSearchBox() {
        inputSearch.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                loadAPIData(s.toString(),1)
            }
        })
    }

    fun loadAPIData(input: String, page:Int) {
        viewModel = ViewModelProvider(this).get(SearchActivityViewModel::class.java)
        viewModel.getMovieListObserver().observe(this, Observer<MovieResponse>{
            movieSearchAdapter.movieListData = movieList
            movieSearchAdapter.notifyDataSetChanged()
        })
        viewModel.makeApiCall(input,1)
    }

    }








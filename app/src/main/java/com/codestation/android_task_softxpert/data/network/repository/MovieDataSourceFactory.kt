package com.codestation.android_task_softxpert.data.network.repository

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.codestation.android_task_softxpert.data.network.api.TheMovieDBInterface
import com.codestation.android_task_softxpert.model.Movie
import io.reactivex.disposables.CompositeDisposable

class MovieDataSourceFactory(
    private val apiService: TheMovieDBInterface,
    private val compositeDisposable: CompositeDisposable
) : DataSource.Factory<Int, Movie>() {

    val movieLiveDataSource = MutableLiveData<MovieDataSource>()

    override fun create(): DataSource<Int, Movie> {

        val movieDataSource = MovieDataSource(apiService, compositeDisposable)

        movieLiveDataSource.postValue(movieDataSource)
        return movieDataSource

    }

}
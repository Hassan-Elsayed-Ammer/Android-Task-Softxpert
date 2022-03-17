package com.codestation.android_task_softxpert.ui.single_movie_details

import androidx.lifecycle.LiveData
import com.codestation.android_task_softxpert.data.network.api.TheMovieDBInterface
import com.codestation.android_task_softxpert.data.network.repository.MovieDetailsNetworkDataSource
import com.codestation.android_task_softxpert.data.network.repository.NetworkState
import com.codestation.android_task_softxpert.model.MovieDetails
import io.reactivex.disposables.CompositeDisposable

class MovieDetailsRepo(
    private val apiService: TheMovieDBInterface
) {
    lateinit var movieDetailsNetworkDataSource: MovieDetailsNetworkDataSource

    fun fetchSingleMovieDetails (compositeDisposable: CompositeDisposable, movieId: Int): LiveData<MovieDetails> {

        movieDetailsNetworkDataSource = MovieDetailsNetworkDataSource(apiService,compositeDisposable)
        movieDetailsNetworkDataSource.fetchMovieDetails(movieId)

        return movieDetailsNetworkDataSource.downloadedMovieResponse
    }

    fun getMovieDetailsNetworkState(): LiveData<NetworkState> {
        return  movieDetailsNetworkDataSource.networkState
    }
}
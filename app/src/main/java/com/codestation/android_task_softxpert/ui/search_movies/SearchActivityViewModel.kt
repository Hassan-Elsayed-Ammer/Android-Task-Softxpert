package com.codestation.android_task_softxpert.ui.search_movies


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.codestation.android_task_softxpert.data.network.api.TheMovieDBClient
import com.codestation.android_task_softxpert.model.MovieResponse
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers


class SearchActivityViewModel: ViewModel() {

    var movieSearchList: MutableLiveData<MovieResponse>
    init {
        movieSearchList = MutableLiveData()
    }

    fun getMovieListObserver(): MutableLiveData<MovieResponse> {
        return movieSearchList
    }

    fun makeApiCall(query: String , page:Int) {

        val retroInstance  = TheMovieDBClient.getClient()
        retroInstance.searchMovie(query , page)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(getMovieListObserverRx())
    }

    private fun getMovieListObserverRx(): Observer<MovieResponse> {
        return object : Observer<MovieResponse> {
            override fun onComplete() {
            }

            override fun onError(e: Throwable) {
                movieSearchList.postValue(null)
            }

            override fun onNext(t: MovieResponse) {
                movieSearchList.postValue(t)
            }

            override fun onSubscribe(d: Disposable) {
            }
        }
    }



}



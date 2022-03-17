package com.codestation.android_task_softxpert.data.network.api


import com.codestation.android_task_softxpert.model.MovieDetails
import com.codestation.android_task_softxpert.model.MovieResponse
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TheMovieDBInterface {


    //get (All)Popular Movie Get(All) Movies
    @GET("movie/popular")
    fun getPopularMovie(@Query("page")page :Int): Single<MovieResponse>

    /**function to get Single Movie Details by Id (Retrieve Single Movie)
     * @Path tall retrofit this (id:Int) is a {movie_id}
     * this function return single observable of data stream in (RXJava)  of Movie Details
     */
    //get (Single) movie by Id
    //https://api.themoviedb.org/3/movie/419704?api_key=b221d5b45326b0199aaee44e3c20acf6
    @GET( "movie/{movie_id}" )
    fun getMovieDetails (@Path("movie_id" ) id :Int) :Single<MovieDetails>


    //Search function
    @GET("search/movie")
    fun searchMovie(
        @Query("query") query: String,
        @Query("page") page: Int): Observable<MovieResponse>

}







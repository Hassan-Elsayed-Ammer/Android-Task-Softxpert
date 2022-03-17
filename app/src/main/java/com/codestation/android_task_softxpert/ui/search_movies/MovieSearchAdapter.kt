package com.codestation.android_task_softxpert.ui.search_movies

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.codestation.android_task_softxpert.R
import com.codestation.android_task_softxpert.data.network.api.POSTER_BASE_URL
import com.codestation.android_task_softxpert.model.Movie
import com.codestation.android_task_softxpert.ui.single_movie_details.SingleMovie
import kotlinx.android.synthetic.main.movie_list_item.view.*

class MovieSearchAdapter(
    val context: Context
): RecyclerView.Adapter<MovieSearchAdapter.MovieItemViewHolder>() {

    var movieListData = ArrayList<Movie>()

    override fun onCreateViewHolder(  parent: ViewGroup, viewType: Int ): MovieSearchAdapter.MovieItemViewHolder {

        val inflater = LayoutInflater.from(parent.context).inflate(R.layout.movie_list_item, parent, false )
        return MovieItemViewHolder(inflater)
    }

    override fun onBindViewHolder(holder: MovieSearchAdapter.MovieItemViewHolder, position: Int) {
        holder.bind(movieListData[position],context)

    }

    override fun getItemCount(): Int {
        return movieListData.size
    }

    class MovieItemViewHolder (view: View) : RecyclerView.ViewHolder(view) {
        fun bind(movie: Movie?,context: Context) {
            itemView.cv_movie_title.text = movie?.title
            itemView.cv_movie_release_date.text =  movie?.releaseDate
            val moviePosterURL = POSTER_BASE_URL + movie?.posterPath
            Glide.with(itemView.context)
                .load(moviePosterURL)
                .into(itemView.cv_iv_movie_poster)

            itemView.setOnClickListener{
                val intent = Intent(context, SingleMovie::class.java)
                intent.putExtra("id", movie?.id)
                context.startActivity(intent)
            }
        }
    }

}
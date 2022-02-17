package com.example.themovies.view.SearchActivity

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.themovies.R
import com.example.themovies.data.model.Movie
import com.example.themovies.data.net.RetrofitClient
import com.squareup.picasso.Picasso

interface RecycleItemListener{
    fun onItemClick(view : View, movie : Movie)
}

class SearchAdapter(var movieList : MutableList<Movie>, var listener: RecycleItemListener)
    :RecyclerView.Adapter<SearchAdapter.SearchMovieHolder>() {

    inner class SearchMovieHolder(view : View) : RecyclerView.ViewHolder(view){
        var imgMovieImage : ImageView = view.findViewById(R.id.imgMovieImage)
        var tvMovieTitle : TextView = view.findViewById(R.id.tvMovieTitle)
        var tvMovieReleaseDate : TextView = view.findViewById(R.id.tvMovieReleaseDate)
        var tvOverView : TextView = view.findViewById(R.id.tvOverView)

        init {
            view.setOnClickListener {
                listener.onItemClick(view,movieList[adapterPosition])
            }
        }

        fun bind(movie: Movie){
            tvMovieTitle.setText(movie.title)
            tvMovieReleaseDate.setText(movie.getReleaseYearFromDate())
            tvOverView.setText(movie.overview)

            if(movie.posterPath != null){
                Picasso.get().load(RetrofitClient.IMAGEURL + movie.posterPath).into(imgMovieImage)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchMovieHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_movie_search,parent,false)
        return SearchMovieHolder(view)
    }

    override fun onBindViewHolder(holder: SearchMovieHolder, position: Int) {
        holder.bind(movieList[position])
    }

    override fun getItemCount(): Int {
        return movieList.size
    }

    fun setMovies(movieList : List<Movie>){
        this.movieList.clear()
        this.movieList.addAll(movieList)
        notifyDataSetChanged()
    }


}
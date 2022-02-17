package com.example.themovies.view.MainActivity

import android.content.Context
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.themovies.R
import com.example.themovies.databinding.ItemMovieListBinding
import com.example.themovies.data.model.Movie
import com.example.themovies.data.net.RetrofitClient
import com.squareup.picasso.Picasso

class MovieAdapter(context: Context, movieList : MutableList<Movie>) :
    RecyclerView.Adapter<MovieAdapter.MovieHolder>() {
    private var context: Context
    private var movieList: MutableList<Movie>
    private var selectedMovie : HashSet<Movie>
    init {
        this.context = context
        this.movieList = movieList
        selectedMovie = HashSet()
    }
    inner class MovieHolder(val binding : ItemMovieListBinding) : RecyclerView.ViewHolder(binding.root){


        fun bind(movie : Movie){
            binding.tvMovieTitle.text = movie.title
            binding.tvMovieReleaseDate.text = movie.releaseDate

            binding.cbCheckBox.setOnClickListener(View.OnClickListener {
                val position = adapterPosition
                if(!selectedMovie.contains(movieList.get(position))){
                    selectedMovie.add(movieList.get(position))
                    binding.cbCheckBox.isChecked = true
                }else{
                    selectedMovie.remove(movieList.get(position))
                    binding.cbCheckBox.isChecked = false
                }
            })

            if(TextUtils.isEmpty(movie.posterPath)){
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                    binding.imgMovieImage.setImageDrawable(context.getDrawable(R.drawable.ic_local_movies_gray))
                }
            }else{
                Picasso.get().load(RetrofitClient.IMAGEURL + movie.posterPath).into(binding.imgMovieImage)
            }

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieHolder {
        val layoutInflater = LayoutInflater.from(context)
        val binding = DataBindingUtil.inflate<ItemMovieListBinding>(layoutInflater,R.layout.item_movie_list,parent,false)
        return MovieHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieHolder, position: Int) {

        holder.bind(movieList[position])
    }

    override fun getItemCount(): Int {
        return movieList.size
    }

    fun getSelectedMovie() : HashSet<Movie>{
        return selectedMovie
    }

    fun setListMovies(movieList : List<Movie>){
        this.movieList.clear()
        this.movieList.addAll(movieList)
        notifyDataSetChanged()

    }
}
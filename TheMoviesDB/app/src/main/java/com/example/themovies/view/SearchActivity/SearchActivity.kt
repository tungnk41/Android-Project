package com.example.themovies.view.SearchActivity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.themovies.R
import com.example.themovies.data.model.Movie
import com.example.themovies.view.MainActivity.MainActivity
import com.example.themovies.viewmodel.SearchViewModel

class SearchActivity : AppCompatActivity() {
    private lateinit var rvListSearchMovie : RecyclerView
    private lateinit var tvNoContent : TextView
    private lateinit var pbContentLoading : ProgressBar
    private lateinit var viewModel : SearchViewModel

    private var  adapter = SearchAdapter(mutableListOf(), object : RecycleItemListener{
        override fun onItemClick(view: View, movie: Movie) {
            handleClickItem(view,movie)
        }
    })

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_movie)

        setupView()

    }

    override fun onStart() {
        super.onStart()
        pbContentLoading.visibility = View.VISIBLE
        intent.getStringExtra("SEARCH_QUERY")?.let {
            viewModel.searchMovies(it).observe(this, Observer { movies ->
                if(movies.isEmpty()){
                    pbContentLoading.visibility = View.INVISIBLE
                    rvListSearchMovie.visibility = View.INVISIBLE
                    tvNoContent.visibility = View.VISIBLE
                }else{
                    pbContentLoading.visibility = View.INVISIBLE
                    rvListSearchMovie.visibility = View.VISIBLE
                    tvNoContent.visibility = View.INVISIBLE
                    adapter.setMovies(movies)
                }

            })
        }
    }

    fun setupView(){
        rvListSearchMovie = findViewById(R.id.rvListSearchMovie)
        tvNoContent = findViewById(R.id.tvNoContent)
        pbContentLoading = findViewById(R.id.pbContentLoading)

        rvListSearchMovie.layoutManager = LinearLayoutManager(this)

        viewModel = ViewModelProvider(this).get(SearchViewModel::class.java)
        rvListSearchMovie.adapter = adapter
    }

    fun handleClickItem(view: View, movie: Movie){
        viewModel.saveMovie(movie)
        startActivity(Intent(this,MainActivity::class.java))
    }

}
package com.example.themovies.view.MainActivity

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.themovies.R
import com.example.themovies.view.AddMovieActivity.AddMovieActivity
import com.example.themovies.viewmodel.MainViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {
    private lateinit var rvListMovie : RecyclerView
    private lateinit var noContentLayout : LinearLayout
    private lateinit var fabFloatButton : FloatingActionButton
    private lateinit var movieAdapter: MovieAdapter
    private lateinit var viewModel : MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupView()

    }


    fun setupView(){
        rvListMovie = findViewById(R.id.rvListMovies)
        rvListMovie.layoutManager = LinearLayoutManager(this)
        noContentLayout = findViewById(R.id.noContentLayout)
        fabFloatButton = findViewById(R.id.fabFloatButton)
        movieAdapter = MovieAdapter(this, mutableListOf())
        rvListMovie.adapter = movieAdapter

        fabFloatButton.setOnClickListener {
            goToAddMovieActivity()
        }

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        viewModel.getAllMovies().observe(this, Observer { movies ->
            if(movies.isEmpty()){
                rvListMovie.visibility = View.INVISIBLE
                noContentLayout.visibility = View.VISIBLE
            }else{
                rvListMovie.visibility = View.VISIBLE
                noContentLayout.visibility = View.INVISIBLE
                movieAdapter.setListMovies(movies)
            }
        })
    }


    fun goToAddMovieActivity(){
        val intent = Intent(this, AddMovieActivity::class.java)
        startActivity(intent)
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.btnDeleteItem){
            if(movieAdapter != null){
                viewModel.deleteMovies(movieAdapter.getSelectedMovie())
            }
        }
        return super.onOptionsItemSelected(item)
    }

}
package com.example.themovies.view.AddMovieActivity

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.*
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.themovies.R
import com.example.themovies.data.model.Movie
import com.example.themovies.view.SearchActivity.SearchActivity
import com.example.themovies.data.net.RetrofitClient
import com.example.themovies.databinding.ActivityAddMovieBinding
import com.example.themovies.viewmodel.AddMovieViewModel
import com.squareup.picasso.Picasso

class AddMovieActivity : AppCompatActivity(){

    private lateinit var dataBinding  : ActivityAddMovieBinding
    private lateinit var viewModel : AddMovieViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_add_movie)
        viewModel = ViewModelProvider(this).get(AddMovieViewModel::class.java)
        dataBinding.viewModel = viewModel
        setupView()
    }

    fun setupView(){

        dataBinding.btnSearch.setOnClickListener {
            goToSearchActivity()
        }

        dataBinding.btnAddMovie.setOnClickListener {
            onClickAddMovie()
        }
    }


    fun goToSearchActivity(){
        if(!TextUtils.isEmpty(dataBinding.edtTitle.text.toString())){
            val intent = Intent(this, SearchActivity::class.java)
            intent.putExtra("SEARCH_QUERY",dataBinding.edtTitle.text.toString())
            startActivity(intent)
        }
        else{
            Toast.makeText(this,"Please enter Movie title",Toast.LENGTH_SHORT).show()
        }
    }

    fun onClickAddMovie(){
            val title: String = dataBinding.edtTitle.text.toString()
            val releaseDate : String = dataBinding.edtReleaseDate.text.toString()

        if(title.isNotBlank()){
            viewModel.saveMovie(Movie(title = title,releaseDate = releaseDate))
            finish()
        }
    }
}
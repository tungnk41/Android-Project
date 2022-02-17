package com.example.themovies.viewmodel

import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.themovies.data.MovieRepositoryImp
import com.example.themovies.data.model.Movie
import java.util.*

class AddMovieViewModel : ViewModel(){
    private val repository = MovieRepositoryImp()

    fun saveMovie(movie : Movie){
        repository.insert(movie)
    }
}
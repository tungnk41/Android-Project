package com.example.themovies.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.themovies.data.MovieRepositoryImp
import com.example.themovies.data.model.Movie

class SearchViewModel : ViewModel(){
    private val repository = MovieRepositoryImp()

    fun searchMovies(query : String) : LiveData<List<Movie>>{
        return repository.searchMovies(query)
    }

    fun saveMovie(movie: Movie){
        repository.insert(movie)
    }
}
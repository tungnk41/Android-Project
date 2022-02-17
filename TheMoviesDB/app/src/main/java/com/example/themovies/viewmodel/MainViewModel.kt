package com.example.themovies.viewmodel

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import com.example.themovies.data.MovieRepositoryImp
import com.example.themovies.data.model.Movie

class MainViewModel() : ViewModel(){
    private val repository = MovieRepositoryImp()

    private val allMovies = MediatorLiveData<List<Movie>>()

    init{
        allMovies.addSource(repository.getAll()){
            allMovies.postValue(it)
        }
    }

    fun deleteMovies(movieList : HashSet<Movie>){
        for(movie in movieList){
            repository.delete(movie)
        }
    }

    fun getAllMovies() = allMovies


}
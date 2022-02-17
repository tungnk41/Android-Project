package com.example.themovies.data

import androidx.lifecycle.LiveData
import com.example.themovies.data.model.Movie

interface MovieRepository {
    fun getAll() : LiveData<List<Movie>>

    fun insert(movie : Movie)

    fun delete(movie : Movie)

    fun deleteAll()

    fun update(movie: Movie)

    fun searchMovies(query : String) : LiveData<List<Movie>>

}
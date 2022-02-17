package com.example.themovies.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.themovies.App
import com.example.themovies.data.database.MovieDao
import com.example.themovies.data.database.MovieDatabase
import com.example.themovies.data.model.Movie
import com.example.themovies.data.model.MovieResponse
import com.example.themovies.data.net.RetrofitClient
import com.example.themovies.database
import kotlinx.coroutines.runBlocking
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MovieRepositoryImp : MovieRepository {
    private val movieDao : MovieDao = database.movieDao()
    private val retrofitClient = RetrofitClient()

    override fun getAll(): LiveData<List<Movie>> {
        return movieDao.getAll()
    }

    override fun insert(movie: Movie) {
        runBlocking {
            movieDao.insert(movie)
        }
    }

    override fun delete(movie: Movie) {
        runBlocking {
            movieDao.delete(movie.id)
        }

    }

    override fun deleteAll() {
        runBlocking{
            movieDao.deleteAll()
        }
    }

    override fun update(movie: Movie) {
        runBlocking{
            movieDao.update(movie)
        }
    }

    override fun searchMovies(query: String): LiveData<List<Movie>> {
        Log.d("TAG", "searchMovies: " + query)
        val data = MutableLiveData<List<Movie>>()

        retrofitClient.searchMovies(query).enqueue(object : Callback<MovieResponse>{
            override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
                data.value = response.body()?.results
                Log.d(this.javaClass.simpleName, "Response : " + data.value)
            }
            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                data.value = null
                Log.d(this.javaClass.simpleName, "Failure")
            }
        })

        Log.d("TAG", "searchMovies:  done " + data.value)
        return data
    }
}
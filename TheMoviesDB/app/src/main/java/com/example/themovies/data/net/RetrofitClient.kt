package com.example.themovies.data.net

import com.example.themovies.data.model.MovieResponse
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitClient {
    private val movieApi : MovieApi
    companion object{
        const val API_KEY = "e8b165723828dcc8244fcee5f089d3e3"
        const val BASE_URL = "http://api.themoviedb.org/3/"
        const val IMAGEURL = "https://image.tmdb.org/t/p/w500/"
    }

    init{
        val builder = OkHttpClient.Builder()
        val okHttpClient = builder.build()
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
        movieApi = retrofit.create(MovieApi::class.java)
    }


    fun searchMovies(query: String) : Call<MovieResponse> {
        return movieApi.searchMovie(API_KEY,query)
    }
}
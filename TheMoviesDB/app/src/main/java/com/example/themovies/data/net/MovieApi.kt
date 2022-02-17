package com.example.themovies.data.net

import com.example.themovies.data.model.MovieResponse
import io.reactivex.Observable
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieApi {

    @GET("search/movie")
    fun searchMovie(
        @Query("api_key") apiKey: String,
        @Query("query") q: String
    ) : Call<MovieResponse>
}
package com.example.themovies.data.model

import com.example.themovies.data.model.Movie
import com.google.gson.annotations.SerializedName

class MovieResponse {
    @SerializedName("page")
    var page: Int? = null

    @SerializedName("total_results")
    var totalResults : Int? = null

    @SerializedName("total_pages")
    var totalPages : Int? = null

    @SerializedName("results")
    var results : List<Movie>? = null

}
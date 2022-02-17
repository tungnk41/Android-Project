package com.example.recipes.Model.Service

import com.example.recipes.Model.Entity.Recipe
import com.example.recipes.Model.Paging.PagedResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface RecipeApi {
    @GET("character/")
    suspend fun getAllRecipe(@Query("page") page : Int) : Response<PagedResponse<Recipe>>
}
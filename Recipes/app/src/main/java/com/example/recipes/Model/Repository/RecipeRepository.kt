package com.example.recipes.Model.Repository

import com.example.recipes.Model.Entity.Recipe

interface RecipeRepository {

    suspend fun insertList(recipeList : List<Recipe>)

    suspend fun deleteAll()

    fun getAllRecipes() : List<Recipe>
}
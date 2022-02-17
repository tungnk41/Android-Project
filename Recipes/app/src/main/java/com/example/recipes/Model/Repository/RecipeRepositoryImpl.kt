package com.example.recipes.Model.Repository

import androidx.lifecycle.LiveData
import androidx.room.Query
import com.example.recipes.Model.Database.RecipeDatabase
import com.example.recipes.Model.Entity.Recipe
import com.example.recipes.Model.Service.RecipeApi

class RecipeRepositoryImpl(database : RecipeDatabase, service : RecipeApi) : RecipeRepository {
    private val recipeDao = database.recipeDao()


    override suspend fun insertList(recipeList : List<Recipe>){
        recipeDao.insertList(recipeList)
    }

    override suspend fun deleteAll(){
        recipeDao.deleteAll()
    }

    override fun getAllRecipes() : List<Recipe>{
        return recipeDao.getAllRecipes()
    }

}
package com.example.recipes.Model.Database

import androidx.room.*
import com.example.recipes.Model.Entity.Recipe

@Dao
interface RecipeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertList(recipeList : List<Recipe>)

    @Query("DELETE FROM RECEIPES_TABLE")
    suspend fun deleteAll()

    @Query("SELECT * FROM RECEIPES_TABLE")
    fun getAllRecipes() : List<Recipe>


}
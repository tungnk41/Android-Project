package com.example.recipes.Model.Database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.recipes.Model.Entity.Recipe

@Database(entities = [Recipe::class], version = 1)
abstract class RecipeDatabase : RoomDatabase() {
    abstract fun recipeDao() : RecipeDao

}
package com.example.recipes.Hilt

import com.example.recipes.Model.Repository.RecipeRepository
import com.example.recipes.Model.Repository.RecipeRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindRecipeRepository(recipeRepository : RecipeRepositoryImpl) : RecipeRepository
}
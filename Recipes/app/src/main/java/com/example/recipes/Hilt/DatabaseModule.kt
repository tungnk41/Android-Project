package com.example.recipes.Hilt

import android.content.Context
import androidx.room.Room
import com.example.recipes.Model.Database.RecipeDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context : Context) : RecipeDatabase{
        return Room.databaseBuilder(context,RecipeDatabase::class.java,"RECIPE_DB")
            .build()
    }
}
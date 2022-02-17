package com.example.themovies

import android.app.Application
import com.example.themovies.data.database.MovieDatabase

lateinit var database : MovieDatabase

class App : Application() {

    companion object{
        lateinit var INSTANCE : App
    }

    override fun onCreate() {
        super.onCreate()

        database = MovieDatabase.getInstance(this)!!
        INSTANCE = this
    }
}
package com.example.themovies.data.database

import android.app.Application
import androidx.room.*
import com.example.themovies.data.model.Movie

@Database(entities = [Movie::class],version = 1)
@TypeConverters(TypeConvertRoom::class)
abstract class MovieDatabase : RoomDatabase() {

    abstract fun movieDao(): MovieDao

    companion object{
        private var INSTANCE: MovieDatabase? = null
        private const val DB_NAME: String = "MovieDatabase"
        private val lock = Any()

        fun getInstance(application: Application) : MovieDatabase? {
            synchronized(lock){
                if(INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(application,MovieDatabase::class.java,DB_NAME)
//                        .allowMainThreadQueries()
                        .build()
                }
            }
            return INSTANCE!!
        }
    }
}
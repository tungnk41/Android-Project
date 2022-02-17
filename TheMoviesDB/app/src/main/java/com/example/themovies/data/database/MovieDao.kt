package com.example.themovies.data.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.themovies.data.model.Movie

@Dao
interface MovieDao {
    @Query("SELECT * FROM movie_table")
    fun getAll(): LiveData<List<Movie>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(movie: Movie)

    @Query("DELETE FROM movie_table WHERE id = :id ")
    suspend fun delete(id : Int?)

    @Query("DELETE FROM movie_table")
    suspend fun deleteAll()

    @Update
    suspend fun update(movie: Movie)
}
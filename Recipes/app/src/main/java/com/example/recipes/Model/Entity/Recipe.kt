package com.example.recipes.Model.Entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "RECEIPES_TABLE")
data class Recipe(
    @PrimaryKey
    @SerializedName("name")
    var name : String,
    @SerializedName("image")
    var urlImage : String
)

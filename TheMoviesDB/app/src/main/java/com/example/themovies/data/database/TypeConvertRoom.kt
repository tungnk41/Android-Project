package com.example.themovies.data.database

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class TypeConvertRoom {
    @TypeConverter
    fun stringToListInteger(data : String?) : List<Int>{

        if(data == null || data.isEmpty() || data.equals("null")){
            return listOf<Int>()
        }

        val listType = object : TypeToken<List<Int>>() {

        }.type

        return Gson().fromJson(data, listType)
    }

    @TypeConverter
    fun listIntegerToString(data : List<Int>?) : String{
        return Gson().toJson(data)
    }
}
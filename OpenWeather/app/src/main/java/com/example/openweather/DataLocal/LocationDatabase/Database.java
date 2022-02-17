package com.example.openweather.DataLocal.LocationDatabase;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;


public class Database {

    private static final String DATABASE_NAME = "location.db";
    private static final int DATABASE_VERSION = 1;

    private DatabaseSQLiteHelper dbHelper;
    public LocationDAO locationDAO;


    public class DatabaseSQLiteHelper extends SQLiteAssetHelper {
        public DatabaseSQLiteHelper(@Nullable Context context) {
            super(context, DATABASE_NAME,null, DATABASE_VERSION);
        }
    }


    public Database(Context context) {
        dbHelper = new DatabaseSQLiteHelper(context);
        locationDAO = new LocationDAOImpl(this);
    }

    public SQLiteDatabase open(){
        return dbHelper.getReadableDatabase();
    }

    public void close(){
        dbHelper.close();
    }

}

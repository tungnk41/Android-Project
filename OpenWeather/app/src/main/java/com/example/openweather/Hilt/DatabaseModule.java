package com.example.openweather.Hilt;

import android.content.Context;

import com.example.openweather.DataLocal.LocationDatabase.Database;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public class DatabaseModule {

    @Singleton
    @Provides
    Database provideLocationDatabase(@ApplicationContext Context context){
        return new Database(context);
    }

}

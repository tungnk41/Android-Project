package com.example.openweather.Hilt;

import android.content.Context;

import com.example.openweather.DataLocal.SharePreferencesManager;
import com.example.openweather.Utils.CoordinateHelper;
import com.example.openweather.Utils.ThreadPoolHelper;
import com.example.openweather.Utils.WeatherUtils;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public class UtilsModule {

    @Provides
    @Singleton
    WeatherUtils provideWeatherUtils(@ApplicationContext Context context, SharePreferencesManager sharePreferencesManager){
        return new WeatherUtils(context, sharePreferencesManager);
    }

    @Provides
    @Singleton
    CoordinateHelper provideCoodinateHelper(@ApplicationContext Context context, SharePreferencesManager sharePreferencesManager){
        return new CoordinateHelper(context, sharePreferencesManager);
    }

    @Provides
    @Singleton
    ThreadPoolHelper provideThreadPoolHelper(){
        return new ThreadPoolHelper();
    }

}

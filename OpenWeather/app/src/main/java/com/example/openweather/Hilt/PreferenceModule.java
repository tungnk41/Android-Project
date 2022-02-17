package com.example.openweather.Hilt;

import android.content.Context;

import com.example.openweather.DataLocal.SharePreferences;
import com.example.openweather.DataLocal.SharePreferencesManager;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public class PreferenceModule {

    @Singleton
    @Provides
    SharePreferences providePreferences(@ApplicationContext Context context){
        return new SharePreferences(context);
    }

    @Singleton
    @Provides
    SharePreferencesManager providePreferencesManager(SharePreferences sharePreferences){
        return new SharePreferencesManager(sharePreferences);
    }
}

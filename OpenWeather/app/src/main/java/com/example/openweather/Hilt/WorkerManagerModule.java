package com.example.openweather.Hilt;

import android.content.Context;

import com.example.openweather.WorkerManager.WorkerManager;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public class WorkerManagerModule {

    @Provides
    @Singleton
    WorkerManager provideWorkerManager(@ApplicationContext Context context){
        return new WorkerManager(context);
    }
}

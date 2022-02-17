package com.example.openweather.WorkerManager;

import android.content.Context;

import androidx.work.Constraints;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.NetworkType;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import java.util.concurrent.TimeUnit;

import dagger.hilt.android.qualifiers.ApplicationContext;
import timber.log.Timber;

public class WorkerManager {
    private static final String WORKER_UPDATE_WEATHER = "Update_Weather_Worker";
    private PeriodicWorkRequest updateWeatherWorkRequest;
    private Context context;

    public WorkerManager(@ApplicationContext  Context context) {
        this.context = context;
    }


    public void startRunUpdateWeatherWorker(int interval){
        Timber.d("WorkerManager startRunUpdateWeatherWorker : " + interval);
        Constraints.Builder builder = new Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED);
        updateWeatherWorkRequest = new PeriodicWorkRequest.Builder(UpdateWeatherWorker.class,15, TimeUnit.MINUTES,5,TimeUnit.MINUTES)
                .setConstraints(builder.build())
                .addTag(WORKER_UPDATE_WEATHER)
                .build();
        WorkManager.getInstance(context).enqueueUniquePeriodicWork(WORKER_UPDATE_WEATHER, ExistingPeriodicWorkPolicy.REPLACE,updateWeatherWorkRequest);
    }

    public void stopAllWorker(){
        Timber.d("WorkerManager stopAllWorker");
        WorkManager.getInstance(context).cancelAllWork();
    }
}

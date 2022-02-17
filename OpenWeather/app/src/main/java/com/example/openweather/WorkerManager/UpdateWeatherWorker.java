package com.example.openweather.WorkerManager;

import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

public class UpdateWeatherWorker extends Worker {
    private static final String BROADCAST_UPDATE_WEATHER_ACTION = "broadcast.update.weather.action";

    public UpdateWeatherWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        getApplicationContext().sendBroadcast(new Intent(BROADCAST_UPDATE_WEATHER_ACTION));
        return Result.success();
    }
}

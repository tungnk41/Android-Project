package com.example.openweather;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;

import com.example.openweather.Utils.ThreadPoolHelper;

import dagger.hilt.android.HiltAndroidApp;
import timber.log.Timber;

@HiltAndroidApp
public class AppWeather extends Application {
    public static final String CHANNEL_ID = "Channel 1";

    @Override
    public void onCreate() {
        super.onCreate();
        createNotificationChannel();
        if(BuildConfig.DEBUG){
            Timber.plant(new Timber.DebugTree());
        }
    }

    public void createNotificationChannel(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel serviceChannel = new NotificationChannel(
                    CHANNEL_ID,
                    "Notification Service Channel",
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(serviceChannel);
        }
    }

}

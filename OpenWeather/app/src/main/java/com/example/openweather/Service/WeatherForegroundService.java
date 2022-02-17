package com.example.openweather.Service;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.IBinder;
import android.widget.RemoteViews;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import static com.example.openweather.AppWeather.CHANNEL_ID;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.openweather.DataLocal.SharePreferencesManager;
import com.example.openweather.Hilt.Qualifier.RemoteRepository;
import com.example.openweather.Model.CurrentWeather.CurrentWeather;
import com.example.openweather.R;
import com.example.openweather.Repository.Repository;
import com.example.openweather.Utils.WeatherUtils;
import com.example.openweather.View.MainActivity;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import timber.log.Timber;

@AndroidEntryPoint
public class WeatherForegroundService extends Service {
    private static final String BROADCAST_UPDATE_WEATHER_ACTION = "broadcast.update.weather.action";
    private static final String BROADCAST_UPDATE_WEATHER_UNIT_ACTION = "broadcast.update.weather.unit.action";


    private NotificationCompat.Builder notificationBuilder;
    private NotificationManager notificationManager;
    private RemoteViews remoteViews;
    private BroadcastReceiver broadcastReceiver;

    private int temperature;
    private boolean isUpdatingData = false;

    @Inject
    @RemoteRepository
    Repository repository;

    @Inject
    SharePreferencesManager sharePreferencesManager;

    @Inject
    WeatherUtils weatherUtils;

    @Override
    public void onCreate() {
        Timber.d("WeatherForegroundService : onCreate");
        super.onCreate();
        initNotiBuilder();
        initBroadcastReceiver();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Timber.d("WeatherForegroundService : onStartCommand");
        startForeground(intent);
        return START_STICKY;
    }

    void initNotiBuilder(){
        notificationManager = (NotificationManager) getSystemService(getApplicationContext().NOTIFICATION_SERVICE);
        notificationBuilder = new NotificationCompat.Builder(this, CHANNEL_ID);
        remoteViews = new RemoteViews(getPackageName(), R.layout.fg_service_weather);
        notificationBuilder
                .setContentTitle("Weather Service")
                .setContent(remoteViews)
                .setSmallIcon(R.drawable.ic_splash);
    }

    void initBroadcastReceiver(){
        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Timber.d("WeatherForegroundService Broadcast FG : " + intent.getAction());
                if(intent.getAction().equals(BROADCAST_UPDATE_WEATHER_ACTION)){
                    requestUpdateCurrentWeatherService();
                    return;
                }
                if(intent.getAction().equals(BROADCAST_UPDATE_WEATHER_UNIT_ACTION)){
                    updateRemoteViewUnit();
                }
            }
        };

        IntentFilter intentFilter = new IntentFilter(BROADCAST_UPDATE_WEATHER_ACTION);
        intentFilter.addAction(BROADCAST_UPDATE_WEATHER_ACTION);
        intentFilter.addAction(BROADCAST_UPDATE_WEATHER_UNIT_ACTION);
        registerReceiver(broadcastReceiver,intentFilter);
    }

    void startForeground(Intent intent){
        Timber.d("WeatherForegroundService : startForeground");
        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,
                0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        notificationBuilder
                .setContentIntent(pendingIntent);

        requestUpdateCurrentWeatherService();
        startForeground(1, notificationBuilder.build());
    }

    void updateRemoteView(String location,String temperature, String imgWeatherCondition, String weatherCondition){
        Timber.d("WeatherForegroundService : updateRemoteView");
        remoteViews.setTextViewText(R.id.fg_tvLocation,location);
        remoteViews.setTextViewText(R.id.fg_tvTemperature,temperature);
        remoteViews.setTextViewText(R.id.fg_tvWeatherCondition,weatherCondition);

        String url = "https://openweathermap.org/img/wn/" + imgWeatherCondition + "@2x.png";
        Glide.with(this)
                .asBitmap()
                .load(url)
                .into(new CustomTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        remoteViews.setImageViewBitmap(R.id.fg_imgWeatherIcon,resource);
                        notificationBuilder
                                .setContent(remoteViews);
                        notificationManager.notify(1, notificationBuilder.build());
                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {

                    }
                });
    }

    void updateRemoteViewUnit(){
        Timber.d("WeatherForegroundService : updateRemoteViewUnit");
        remoteViews.setTextViewText(R.id.fg_tvTemperature,weatherUtils.temperatureUtil(temperature));
        notificationBuilder
                .setContent(remoteViews);
        notificationManager.notify(1, notificationBuilder.build());
    }


    void requestUpdateCurrentWeatherService(){
        if(!isUpdatingData){
            isUpdatingData = true;
            Timber.d("WeatherForegroundService : requestUpdateCurrentWeatherService");
            sendBroadcast(new Intent());
            List<String> _coordinate = sharePreferencesManager.getLastestCoordinate();
            repository.getCurrentWeatherByCoordinate(_coordinate.get(0), _coordinate.get(1), new Repository.ResultCurrentWeather() {
                @Override
                public void onSuccess(CurrentWeather currentWeather) {
                    temperature = (int)currentWeather.getMain().getTemp();
                    String _location = sharePreferencesManager.getLastestLocation();
                    String _temperature = weatherUtils.temperatureUtil(temperature);
                    String _imgWeatherCondition = currentWeather.getListWeather().get(0).getIcon();
                    String _weatherCondition = currentWeather.getListWeather().get(0).getMain();
                    updateRemoteView(_location,_temperature,_imgWeatherCondition,_weatherCondition);
                    isUpdatingData = false;
                }

                @Override
                public void onFailure(Throwable t) {
                    isUpdatingData = false;
                    t.printStackTrace();
                }
            });
        }
    }



    @Override
    public void onDestroy() {
        Timber.d("WeatherForegroundService : onDestroy");
        super.onDestroy();
        unregisterReceiver(broadcastReceiver);
    }
}

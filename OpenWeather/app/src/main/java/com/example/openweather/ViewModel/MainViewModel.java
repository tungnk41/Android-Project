package com.example.openweather.ViewModel;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.openweather.DataLocal.SharePreferencesManager;
import com.example.openweather.Hilt.Qualifier.RemoteRepository;
import com.example.openweather.Model.CurrentWeather.CurrentWeather;
import com.example.openweather.Model.Forecast.Daily;
import com.example.openweather.Model.Forecast.Forecast;
import com.example.openweather.Model.Forecast.Hourly;
import com.example.openweather.R;
import com.example.openweather.Repository.Repository;
import com.example.openweather.Service.WeatherForegroundService;
import com.example.openweather.Utils.CoordinateHelper;
import com.example.openweather.Utils.NetworkHelper;
import com.example.openweather.Utils.WeatherUtils;
import com.example.openweather.WorkerManager.WorkerManager;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import dagger.hilt.android.qualifiers.ApplicationContext;
import timber.log.Timber;

@HiltViewModel
public class MainViewModel extends ViewModel {
    private static final String BROADCAST_UPDATE_WEATHER_ACTION = "broadcast.update.weather.action";
    private static final String BROADCAST_UPDATE_WEATHER_UNIT_ACTION = "broadcast.update.weather.unit.action";
    private static final String BROADCAST_RUN_UPDATE_WEATHER_WORKER = "broadcast.run.update.weather.worker";
    private static final String BROADCAST_DATA_WEATHER_UPDATE_UPDATING = "broadcast.data.weather.update.updating";
    private static final String BROADCAST_DATA_WEATHER_UPDATE_DONE = "broadcast.data.weather.update.done";
    private static final String BROADCAST_DATA_WEATHER_UPDATE_ERROR = "broadcast.data.weather.update.error";

    Repository repository;
    Context context;
    SharePreferencesManager sharePreferencesManager;
    WeatherUtils weatherUtils;
    CoordinateHelper coordinateHelper;
    WorkerManager workerManager;
    NetworkHelper networkHelper;

    public MutableLiveData<String>  tvLocation;
    public MutableLiveData<String>  imgWeatherIcon;
    public MutableLiveData<String>  tvWeatherCondition;
    public MutableLiveData<String>  tvDescription;
    public MutableLiveData<String>  tvTemperature;
    public MutableLiveData<String>  tvFeelsLike;

    public MutableLiveData<String>  tvWindDetailSpeed;
    public MutableLiveData<String>  tvHumidityDetail;
    public MutableLiveData<String>  tvClouds;
    public MutableLiveData<String>  tvPressureDetail;
    public MutableLiveData<String>  tvSunRise;
    public MutableLiveData<String>  tvSunSet;

    public MutableLiveData<String>  tvUpdateTime;

    public MutableLiveData<List<Hourly>> listWeatherHourlyLiveData;
    public MutableLiveData<List<Daily>> listWeatherDailyLiveData;
    private CurrentWeather currentWeather;
    private Set<Integer> stateUpdateCollector;

    SharedPreferences.OnSharedPreferenceChangeListener sharedPreferenceChangeListener;
    private BroadcastReceiver broadcastReceiver;

    private Integer isUpdateCurrentWeatherDone = 1;
    private Integer isUpdateForecastWeatherDone = 2;

    private boolean isUpdatingData;

    @Inject
    public MainViewModel(@ApplicationContext Context context,
                         @RemoteRepository Repository repository,
                         SharePreferencesManager sharePreferencesManager,
                         WeatherUtils weatherUtils,
                         CoordinateHelper coordinateHelper,
                         WorkerManager workerManager,
                         NetworkHelper networkHelper) {
        this.repository = repository;
        this.context = context;
        this.sharePreferencesManager = sharePreferencesManager;
        this.weatherUtils = weatherUtils;
        this.coordinateHelper = coordinateHelper;
        this.workerManager = workerManager;
        this.networkHelper = networkHelper;

        initVariableData();
        initSharePreferenceListener();
        initBroadcastReceiver();
        initWeatherForegroundService();

    }

    /************************************** Fragment Current Weather ******************************/

    private void initVariableData(){
        tvLocation = new MutableLiveData<>();
        tvWeatherCondition = new MutableLiveData<>();
        imgWeatherIcon = new MutableLiveData<>();
        tvDescription = new MutableLiveData<>();
        tvTemperature = new MutableLiveData<>();
        tvFeelsLike = new MutableLiveData<>();

        tvWindDetailSpeed = new MutableLiveData<>();
        tvHumidityDetail = new MutableLiveData<>();
        tvClouds = new MutableLiveData<>();
        tvPressureDetail = new MutableLiveData<>();
        tvSunRise = new MutableLiveData<>();
        tvSunSet = new MutableLiveData<>();

        tvUpdateTime = new MutableLiveData<>();

        listWeatherHourlyLiveData = new MutableLiveData<>();
        listWeatherDailyLiveData = new MutableLiveData<>();

        stateUpdateCollector = new HashSet<>();
    }

    public void getCurrentWeatherByLocation(String location){
        stateUpdateCollector.remove(isUpdateCurrentWeatherDone);
        repository.getCurrentWeatherByLocation(location, new Repository.ResultCurrentWeather() {
            @Override
            public void onSuccess(CurrentWeather _currentWeather) {
                currentWeather = _currentWeather;
                updateCurrentWeather(currentWeather);
                stateUpdateCollector.add(isUpdateCurrentWeatherDone);
                if(stateUpdateCollector.contains(isUpdateForecastWeatherDone)){
                    context.sendBroadcast(new Intent((BROADCAST_DATA_WEATHER_UPDATE_DONE)));
                }
            }

            @Override
            public void onFailure(Throwable t) {
                context.sendBroadcast(new Intent(BROADCAST_DATA_WEATHER_UPDATE_ERROR));
            }
        });
    }

    public void getCurrentWeatherByCoordinate(String latitude, String longitude){
        stateUpdateCollector.remove(isUpdateCurrentWeatherDone);
        repository.getCurrentWeatherByCoordinate(latitude, longitude, new Repository.ResultCurrentWeather() {
            @Override
            public void onSuccess(CurrentWeather _currentWeather) {
                currentWeather = _currentWeather;
                updateCurrentWeather(currentWeather);
                stateUpdateCollector.add(isUpdateForecastWeatherDone);
                if(stateUpdateCollector.contains(isUpdateForecastWeatherDone)){
                    context.sendBroadcast(new Intent((BROADCAST_DATA_WEATHER_UPDATE_DONE)));
                }
            }
            @Override
            public void onFailure(Throwable t) {
                context.sendBroadcast(new Intent(BROADCAST_DATA_WEATHER_UPDATE_ERROR));
            }
        });
    }

    String proxyTvLocation(String s){
        if(sharePreferencesManager.getLastestModeLocation() == 0){
            sharePreferencesManager.putLastestLocation(s);
        }else{
            s = sharePreferencesManager.getLastestLocation();
        }
        return s;
    }
    String proxyTvTemperature(double temp){
        return weatherUtils.temperatureUtil((int)temp);
    }
    String proxyTvFeelsLike(double temp){
        return getStringResource(R.string.str_weather_feels_like_temperature) + " " + weatherUtils.temperatureUtil((int)temp);
    }
    String proxyTvWindDetailSpeed(double speed){
        return getStringResource(R.string.str_weather_detail_wind_speed) + " " + speed +
                getStringResource(R.string.str_weather_detail_wind_speed_unit);
    }
    String proxyTvHumidityDetail(int humidity){
        return  getStringResource(R.string.str_weather_detail_humidity) + " " + humidity +
                getStringResource(R.string.str_weather_detail_humidity_unit);
    }
    String proxyTvClouds(int clouds){
        return  getStringResource(R.string.str_weather_detail_clouds) + " " + clouds +
                getStringResource(R.string.str_weather_detail_clouds_unit);
    }
    String proxyTvPressureDetail(int pressure){
        return getStringResource(R.string.str_weather_detail_pressure) + " " + pressure +
                getStringResource(R.string.str_weather_detail_pressure_unit);
    }
    String proxyTvSunRise(long sunRise){
        return getStringResource(R.string.str_weather_detail_sunrise) + " " +
                weatherUtils.timeFormatter(sunRise);
    }
    String proxyTvSunSet(long sunSet){
        return getStringResource(R.string.str_weather_detail_sunset) + " " +
                weatherUtils.timeFormatter(sunSet);
    }
    String proxyUpdateTime(String time){
        return getStringResource(R.string.str_weather_update_time) + " " + time;
    }

    private void updateCurrentWeather(CurrentWeather currentWeather){
        tvLocation.setValue(proxyTvLocation(currentWeather.getName()+ ", " + currentWeather.getSys().getCountry()));
        imgWeatherIcon .setValue(currentWeather.getListWeather().get(0).getIcon());
        tvWeatherCondition .setValue(currentWeather.getListWeather().get(0).getMain());
        tvDescription .setValue(currentWeather.getListWeather().get(0).getDescription());
        tvTemperature .setValue(proxyTvTemperature(currentWeather.getMain().getTemp()));
        tvFeelsLike .setValue(proxyTvFeelsLike(currentWeather.getMain().getFeelsLike()));

        tvWindDetailSpeed.setValue(proxyTvWindDetailSpeed(currentWeather.getWind().getSpeed()));
        tvHumidityDetail .setValue(proxyTvHumidityDetail(currentWeather.getMain().getHumidity()));
        tvClouds .setValue(proxyTvClouds(currentWeather.getClouds().getAll()));
        tvPressureDetail .setValue(proxyTvPressureDetail(currentWeather.getMain().getPressure()));
        tvSunRise .setValue(proxyTvSunRise(currentWeather.getSys().getSunrise()));
        tvSunSet .setValue(proxyTvSunSet(currentWeather.getSys().getSunset()));

        tvUpdateTime.setValue(proxyUpdateTime(sharePreferencesManager.getLastestUpdateTime()));
    }

    private void updateCurrentWeatherUnit(){
        if(currentWeather != null){
            tvTemperature .setValue(proxyTvTemperature(currentWeather.getMain().getTemp()));
            tvFeelsLike .setValue(proxyTvFeelsLike(currentWeather.getMain().getFeelsLike()));
        }
    }
    /**********************************************************************************************/

















    /************************************** Main ViewModel *****************************************/


    void initSharePreferenceListener(){
        sharedPreferenceChangeListener = new SharedPreferences.OnSharedPreferenceChangeListener() {
            @Override
            public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
                Timber.d("MainViewModel : onSharedPreferenceChanged " + key);
                if(key.equals(SharePreferencesManager.KEY_PREF_TEMP_UNIT)){
                    context.sendBroadcast(new Intent(BROADCAST_UPDATE_WEATHER_UNIT_ACTION));
                    return;
                }
                if(key.equals(SharePreferencesManager.KEY_PREF_STATE_RUN_BG_SERVICE)){
                    if(sharePreferencesManager.getStateRunBgService()){
                        context.startForegroundService(new Intent(context, WeatherForegroundService.class));
                    }else{
                        context.stopService(new Intent(context, WeatherForegroundService.class));
                    }
                    return;
                }
                if(key.equals(SharePreferencesManager.KEY_PREF_STATE_AUTO_UPDATE_WEATHER)){
                    if(sharePreferencesManager.getStateAutoUpdateWeather()){
                        context.sendBroadcast(new Intent(BROADCAST_RUN_UPDATE_WEATHER_WORKER));
                    }else{
                        workerManager.stopAllWorker();
                    }
                    return;
                }
                if(key.equals(SharePreferencesManager.KEY_PREF_INTERVAL_AUTO_UPDATE_WEATHER)){
                    if(sharePreferencesManager.getStateAutoUpdateWeather()){
                        context.sendBroadcast(new Intent(BROADCAST_RUN_UPDATE_WEATHER_WORKER));
                    }
                }
            }
        };
        sharePreferencesManager.registerPref(sharedPreferenceChangeListener);
    }

    void initBroadcastReceiver(){
        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Timber.d("Broadcast MainViewModel : " + intent.getAction());
                if(intent.getAction().equals(BROADCAST_UPDATE_WEATHER_UNIT_ACTION)){
                    updateCurrentWeatherUnit();
                    return;
                }
                if(intent.getAction().equals(BROADCAST_UPDATE_WEATHER_ACTION)){
                    updateWeatherData();
                    return;
                }
                if(intent.getAction().equals(ConnectivityManager.CONNECTIVITY_ACTION)){
                    if(networkHelper.isNetworkAvailable() && !isUpdatingData){
                        context.sendBroadcast(new Intent(BROADCAST_UPDATE_WEATHER_ACTION));
                    }
                    return;
                }
                if(intent.getAction().equals(BROADCAST_RUN_UPDATE_WEATHER_WORKER) || intent.getAction().equals(BROADCAST_DATA_WEATHER_UPDATE_DONE)){
                    workerManager.startRunUpdateWeatherWorker(sharePreferencesManager.getIntervalAutoUpdateWeather());
                }
                if(intent.getAction().equals(BROADCAST_DATA_WEATHER_UPDATE_DONE) || intent.getAction().equals(BROADCAST_DATA_WEATHER_UPDATE_ERROR)){
                    isUpdatingData = false;
                }


            }
        };

        IntentFilter intentFilter = new IntentFilter(BROADCAST_UPDATE_WEATHER_UNIT_ACTION);
        intentFilter.addAction(BROADCAST_UPDATE_WEATHER_UNIT_ACTION);
        intentFilter.addAction(BROADCAST_UPDATE_WEATHER_ACTION);
        intentFilter.addAction(BROADCAST_RUN_UPDATE_WEATHER_WORKER);
        intentFilter.addAction(BROADCAST_DATA_WEATHER_UPDATE_DONE);
        intentFilter.addAction(BROADCAST_DATA_WEATHER_UPDATE_ERROR);
        intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        context.registerReceiver(broadcastReceiver,intentFilter);
    }

    void initWeatherForegroundService(){
        if(sharePreferencesManager.getStateRunBgService()){
            context.startForegroundService(new Intent(context, WeatherForegroundService.class));
        }
    }

    public void saveLocationData(String lat, String lon, String location, int modeUpdateLocation){
        sharePreferencesManager.putLastestCoordinate(lat,lon);

        // 1 : Update data from Search Location of Database
        sharePreferencesManager.putLastestModeLocation(modeUpdateLocation);
        sharePreferencesManager.putLastestLocation(location);
    }

    public void saveLastestUpdateTime(){
        DateFormat dateFormat = new SimpleDateFormat("HH:mm");
        sharePreferencesManager.putLastestUpdateTime(dateFormat.format(Calendar.getInstance().getTime()));
    }

    public void requestWeatherDataFromSaveData(){
        context.sendBroadcast(new Intent(BROADCAST_UPDATE_WEATHER_ACTION));
    }

    public void requestWeatherDataByGps(){
        List<String> _coordinateGps = coordinateHelper.getCurrentCoordinateByGps();
        String lat = _coordinateGps.get(0);
        String lon = _coordinateGps.get(1);
        saveLocationData(lat,lon,"",0);
        context.sendBroadcast(new Intent(BROADCAST_UPDATE_WEATHER_ACTION));
    }

    private void updateWeatherData(){
        if(!isUpdatingData){
            isUpdatingData = true;
            context.sendBroadcast(new Intent(BROADCAST_DATA_WEATHER_UPDATE_UPDATING));
            List<String> coordinate = sharePreferencesManager.getLastestCoordinate();
            String lat = coordinate.get(0);
            String lon = coordinate.get(1);
            saveLastestUpdateTime();
            getCurrentWeatherByCoordinate(lat, lon);
            getForecastDataByCoordinate(lat, lon);
        }
    }

    public void getForecastDataByCoordinate(String lat, String lon) {
        stateUpdateCollector.remove(isUpdateForecastWeatherDone);
        repository.getForecastWeatherByCoordinate(lat, lon, new Repository.ResultForecastWeather() {
            @Override
            public void onSuccess(Forecast forecast) {
                listWeatherHourlyLiveData.postValue(forecast.getListHourly());
                listWeatherDailyLiveData.postValue(forecast.getListDaily());
                stateUpdateCollector.add(isUpdateForecastWeatherDone);
                if(stateUpdateCollector.contains(isUpdateCurrentWeatherDone)){
                    context.sendBroadcast(new Intent((BROADCAST_DATA_WEATHER_UPDATE_DONE)));
                }
            }

            @Override
            public void onFailure(Throwable t) {
                context.sendBroadcast(new Intent(BROADCAST_DATA_WEATHER_UPDATE_ERROR));
            }
        });
    }

    private String getStringResource(int id){
        return context.getResources().getString(id);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        sharePreferencesManager.unRegisterPref(sharedPreferenceChangeListener);
        context.unregisterReceiver(broadcastReceiver);
    }
}

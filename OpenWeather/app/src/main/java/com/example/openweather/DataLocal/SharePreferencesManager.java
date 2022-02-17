package com.example.openweather.DataLocal;

import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.List;

public class SharePreferencesManager {

    public static final String KEY_PREF_TEMP_UNIT = "KEY_PREF_TEMP_UNIT";
    public static final String KEY_PREF_STATE_RUN_BG_SERVICE = "KEY_PREF_STATE_RUN_BG_SERVICE";
    public static final String KEY_PREF_STATE_AUTO_UPDATE_WEATHER= "KEY_PREF_STATE_RUN_AUTO_UPDATE_WEATHER";
    public static final String KEY_PREF_INTERVAL_AUTO_UPDATE_WEATHER= "KEY_PREF_INTERVAL_AUTO_UPDATE_WEATHER";

    public static final String LASTEST_LATITUDE = "LASTEST_LATITUDE";
    public static final String LASTEST_LONGITUDE = "LASTEST_LONGITUDE";

    public static final String LASTEST_LOCATION = "LASTEST_LOCATION";

    public static final String LASTEST_UPDATE_TIME = "LASTEST_UPDATE_TIME";
    /*
    mode 0 : Need update by Coord location name
    mode 1 : Need update by Search Location name
    * */
    public static final String LASTEST_MODE_LOCATION = "LASTEST_MODE_LOCATION";



    private SharePreferences sharePreferences;

    public SharePreferencesManager(SharePreferences sharePreferences){
        this.sharePreferences = sharePreferences;
    }

    public void putIntervalAutoUpdateWeather(Integer value){
        sharePreferences.putIntegerValue(KEY_PREF_INTERVAL_AUTO_UPDATE_WEATHER,value);
    }
    public Integer getIntervalAutoUpdateWeather(){
        return sharePreferences.getIntegerValue(KEY_PREF_INTERVAL_AUTO_UPDATE_WEATHER);
    }

    public void putStateAutoUpdateWeather(Boolean value){
        sharePreferences.putBooleanValue(KEY_PREF_STATE_AUTO_UPDATE_WEATHER,value);
    }
    public Boolean getStateAutoUpdateWeather(){
        return sharePreferences.getBooleanValue(KEY_PREF_STATE_AUTO_UPDATE_WEATHER);
    }

    public void putStateRunBgService(boolean value){
        sharePreferences.putBooleanValue(KEY_PREF_STATE_RUN_BG_SERVICE,value);
    }
    public boolean getStateRunBgService(){
        return sharePreferences.getBooleanValue(KEY_PREF_STATE_RUN_BG_SERVICE);
    }

    public void putTempUnit(int value){
        sharePreferences.putIntegerValue(KEY_PREF_TEMP_UNIT,value);
    }
    public int getTempUnit(){
        return sharePreferences.getIntegerValue(KEY_PREF_TEMP_UNIT);
    }


    public  void putLastestCoordinate(String latitude, String longitude){
        sharePreferences.putStringValue(LASTEST_LATITUDE,latitude);
        sharePreferences.putStringValue(LASTEST_LONGITUDE,longitude);
    }
    public List<String> getLastestCoordinate(){
        List<String> result = new ArrayList<>();
        result.add(sharePreferences.getStringValue(LASTEST_LATITUDE));
        result.add(sharePreferences.getStringValue(LASTEST_LONGITUDE));
        return result;
    }


    public void putLastestLocation(String location){
        sharePreferences.putStringValue(LASTEST_LOCATION,location);
    }
    public String getLastestLocation(){
        return sharePreferences.getStringValue(LASTEST_LOCATION);
    }


    // 0 :  Update data from Gps
    // 1 : Update data from Search Location of Database
    public void putLastestModeLocation(int mode){
        sharePreferences.putIntegerValue(LASTEST_MODE_LOCATION,mode);
    }
    public int getLastestModeLocation(){
        return sharePreferences.getIntegerValue(LASTEST_MODE_LOCATION);
    }

    public void putLastestUpdateTime(String time){
        sharePreferences.putStringValue(LASTEST_UPDATE_TIME,time);
    }
    public String getLastestUpdateTime(){
        return sharePreferences.getStringValue(LASTEST_UPDATE_TIME);
    }


    public void registerPref(SharedPreferences.OnSharedPreferenceChangeListener listener){
        sharePreferences.registerPref(listener);
    }
    public void unRegisterPref(SharedPreferences.OnSharedPreferenceChangeListener listener){
        sharePreferences.unRegisterPref(listener);
    }


}

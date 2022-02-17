package com.example.openweather.DataLocal;

import android.content.Context;
import android.content.SharedPreferences;

import dagger.hilt.android.qualifiers.ApplicationContext;

public class SharePreferences {
    private static final String PREFERENCES_STRING = "PREFERENCES_STRING";
    private static final String PREFERENCES_BOOLEAN = "PREFERENCES_BOOLEAN";
    private static final String PREFERENCES_INTEGER = "PREFERENCES_INTEGER";
    private static final String PREFERENCES_LONG = "PREFERENCES_LONG";
    private Context context;

    public SharePreferences(@ApplicationContext Context context) {
        this.context = context;
    }


    public void putStringValue(String key, String value){
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFERENCES_STRING,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key,value);
        editor.apply();
    }

    public String getStringValue(String key){
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFERENCES_STRING,Context.MODE_PRIVATE);
        return sharedPreferences.getString(key,"");
    }

    public void putBooleanValue(String key, Boolean value){
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFERENCES_BOOLEAN,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(key,value);
        editor.apply();
    }

    public Boolean getBooleanValue(String key){
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFERENCES_BOOLEAN,Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(key,false);
    }

    public void putIntegerValue(String key, Integer value){
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFERENCES_INTEGER,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(key,value);
        editor.apply();
    }

    public Integer getIntegerValue(String key){
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFERENCES_INTEGER,Context.MODE_PRIVATE);
        return sharedPreferences.getInt(key,0);
    }

    public void putLongValue(String key, Long value){
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFERENCES_LONG,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putLong(key,value);
        editor.apply();
    }

    public Long getLongValue(String key){
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFERENCES_LONG,Context.MODE_PRIVATE);
        return sharedPreferences.getLong(key,0);
    }


    public void registerPref(SharedPreferences.OnSharedPreferenceChangeListener listener ){
        SharedPreferences sharedPreferencesInteger = context.getSharedPreferences(PREFERENCES_INTEGER,Context.MODE_PRIVATE);
        SharedPreferences sharedPreferencesBoolean = context.getSharedPreferences(PREFERENCES_BOOLEAN,Context.MODE_PRIVATE);
        SharedPreferences sharedPreferencesString = context.getSharedPreferences(PREFERENCES_STRING,Context.MODE_PRIVATE);
        SharedPreferences sharedPreferencesLong = context.getSharedPreferences(PREFERENCES_LONG,Context.MODE_PRIVATE);
        sharedPreferencesInteger.registerOnSharedPreferenceChangeListener(listener);
        sharedPreferencesBoolean.registerOnSharedPreferenceChangeListener(listener);
        sharedPreferencesString.registerOnSharedPreferenceChangeListener(listener);
        sharedPreferencesLong.registerOnSharedPreferenceChangeListener(listener);
    }

    public void unRegisterPref(SharedPreferences.OnSharedPreferenceChangeListener listener ){
        SharedPreferences sharedPreferencesInteger = context.getSharedPreferences(PREFERENCES_INTEGER,Context.MODE_PRIVATE);
        SharedPreferences sharedPreferencesBoolean = context.getSharedPreferences(PREFERENCES_BOOLEAN,Context.MODE_PRIVATE);
        SharedPreferences sharedPreferencesString = context.getSharedPreferences(PREFERENCES_STRING,Context.MODE_PRIVATE);
        SharedPreferences sharedPreferencesLong = context.getSharedPreferences(PREFERENCES_LONG,Context.MODE_PRIVATE);
        sharedPreferencesInteger.unregisterOnSharedPreferenceChangeListener(listener);
        sharedPreferencesBoolean.unregisterOnSharedPreferenceChangeListener(listener);
        sharedPreferencesString.unregisterOnSharedPreferenceChangeListener(listener);
        sharedPreferencesLong.unregisterOnSharedPreferenceChangeListener(listener);
    }
}

package com.example.openweather.Utils;

import android.content.Context;

import com.example.openweather.DataLocal.SharePreferencesManager;
import com.example.openweather.R;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.inject.Inject;

import dagger.hilt.android.qualifiers.ApplicationContext;

public class WeatherUtils {
    private SharePreferencesManager sharePreferencesManager;
    private Context context;

    @Inject
    public WeatherUtils(@ApplicationContext Context context, SharePreferencesManager sharePreferencesManager) {
        this.sharePreferencesManager = sharePreferencesManager;
        this.context = context;
    }

    public enum TempUnit{
        CELSIUS(0),
        FAHRENHEIT(1);

        public int value;
        TempUnit(int _value){
            this.value = _value;
        }
    }

    //Convert unix time to hour
    public String timeFormatter(long milliseconds) {
        Date date = new Date(milliseconds * 1000L);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
        return simpleDateFormat.format(date);
    }

    public String temperatureUtil(int temp) {
        String result = "";
        if (sharePreferencesManager.getTempUnit() == TempUnit.CELSIUS.value) {
            result = String.valueOf(temp) + getStringResource(R.string.str_weather_temperature_unit_c);
        } else if (sharePreferencesManager.getTempUnit() == TempUnit.FAHRENHEIT.value) {
            result = String.valueOf((int) (temp * 1.8 + 32)) + getStringResource(R.string.str_weather_temperature_unit_f);
        } else {
            result = String.valueOf(temp) + getStringResource(R.string.str_weather_temperature_unit_c);
        }

        return result;
    }

    public String dayFormatter(int milliseconds) {
        Date date = new Date(milliseconds * 1000L);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEEE");
        return simpleDateFormat.format(date);
    }

    public String dateFormatter(int milliseconds) {
        Date date = new Date(milliseconds * 1000L);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd");
        return simpleDateFormat.format(date);
    }

    private String getStringResource(int id) {
        return context.getResources().getString(id);
    }
}

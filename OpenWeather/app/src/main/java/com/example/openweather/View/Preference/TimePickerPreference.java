package com.example.openweather.View.Preference;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;

import androidx.annotation.Nullable;
import androidx.preference.DialogPreference;

import timber.log.Timber;

public class TimePickerPreference extends DialogPreference {
    private static final int DEFAULT_MINUTES_FROM_MIDNIGHT = 30;

    public TimePickerPreference(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    public TimePickerPreference(Context context, AttributeSet attrs) {
        super(context,attrs);
    }
    public TimePickerPreference(Context context) {
        super(context);
    }

    public int getPersistedMinutesFromMidnight(){
        return super.getPersistedInt(DEFAULT_MINUTES_FROM_MIDNIGHT);
    }

    public void persistMinutesFromMidnight(int minutesFromMidnight){
        super.persistInt(minutesFromMidnight);
        setSummary(minutesFromMidnightToHourlyTime(minutesFromMidnight));
        callChangeListener(minutesFromMidnight);
    }

    public String minutesFromMidnightToHourlyTime(int minutesFromMidnight){
        String hour = minutesFromMidnight/60 < 10 ? "0" + (minutesFromMidnight/60) : String.valueOf(minutesFromMidnight/60);
        String minute = minutesFromMidnight%60 < 10 ? "0" + (minutesFromMidnight%60) :  String.valueOf(minutesFromMidnight%60);
        String time = hour+ " : " + minute;
        return time;
    }

    @Override
    protected Object onGetDefaultValue(TypedArray a, int index) {
        Timber.d("onGetDefaultValue " + a.getInt(index,-1));
        return a.getInt(index,DEFAULT_MINUTES_FROM_MIDNIGHT);
    }

    @Override
    protected void onSetInitialValue(@Nullable Object defaultValue) {
        super.onSetInitialValue(defaultValue);
        //onSetInitialValue only called in first run application
        if(defaultValue != null){
            Timber.d("onSetInitialValue " + (int)defaultValue);
            persistMinutesFromMidnight((int) defaultValue);
        }
    }

    @Override
    public CharSequence getSummary() {
        return minutesFromMidnightToHourlyTime(getPersistedMinutesFromMidnight());
    }
}

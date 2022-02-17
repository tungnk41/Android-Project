package com.example.openweather.View.Preference;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.preference.DialogPreference;
import androidx.preference.PreferenceDialogFragmentCompat;

public class TimePickerPreferenceDialog extends PreferenceDialogFragmentCompat {
    private static final int MINIMUN_INTERVAL_MINUTES_FROM_MIDNIGHT = 30;
    private TimePicker timePicker;

    public static TimePickerPreferenceDialog newInstance(String key){
        TimePickerPreferenceDialog fragment = new TimePickerPreferenceDialog();
        Bundle bundle = new Bundle(1);
        bundle.putString(ARG_KEY,key);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected View onCreateDialogView(Context context) {
        timePicker = new TimePicker(context);
        return timePicker;
    }

    @Override
    protected void onBindDialogView(View view) {
        super.onBindDialogView(view);

        TimePickerPreference pref = (TimePickerPreference)getPreference();
        timePicker.setIs24HourView(true);
        timePicker.setHour(pref.getPersistedMinutesFromMidnight()/60);
        timePicker.setMinute(pref.getPersistedMinutesFromMidnight()%60);
    }

    @Override
    public void onDialogClosed(boolean positiveResult) {
        if(positiveResult){
            TimePickerPreference pref = (TimePickerPreference)getPreference();
            int newValue = timePicker.getHour()*60 + timePicker.getMinute();
            if(newValue >= MINIMUN_INTERVAL_MINUTES_FROM_MIDNIGHT){
                pref.persistMinutesFromMidnight(newValue);
            }else{
                Toast.makeText(getContext(),"Please set interval larger than 30 minutes",Toast.LENGTH_LONG).show();
            }
        }
    }

}

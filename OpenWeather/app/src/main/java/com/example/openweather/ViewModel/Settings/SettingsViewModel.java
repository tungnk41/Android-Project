package com.example.openweather.ViewModel.Settings;

import androidx.lifecycle.ViewModel;

import com.example.openweather.DataLocal.SharePreferencesManager;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class SettingsViewModel extends ViewModel {

    private SharePreferencesManager sharePreferencesManager;

    @Inject
    public SettingsViewModel(SharePreferencesManager sharePreferencesManager) {
        this.sharePreferencesManager = sharePreferencesManager;
    }

    public void savePreferenceData(String key, Object value){
        switch (key){
            case SharePreferencesManager.KEY_PREF_TEMP_UNIT :
                sharePreferencesManager.putTempUnit(Integer.valueOf(String.valueOf(value)));
                break;
            case SharePreferencesManager.KEY_PREF_STATE_RUN_BG_SERVICE:
                sharePreferencesManager.putStateRunBgService((Boolean) value);
                break;
            case SharePreferencesManager.KEY_PREF_STATE_AUTO_UPDATE_WEATHER:
                sharePreferencesManager.putStateAutoUpdateWeather((Boolean) value);
                break;
            case SharePreferencesManager.KEY_PREF_INTERVAL_AUTO_UPDATE_WEATHER:
                sharePreferencesManager.putIntervalAutoUpdateWeather((Integer) value);
                break;

        }

    }
}

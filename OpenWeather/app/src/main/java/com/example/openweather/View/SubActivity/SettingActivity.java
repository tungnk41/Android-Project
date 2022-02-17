package com.example.openweather.View.SubActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.preference.ListPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.SwitchPreferenceCompat;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.openweather.DataLocal.SharePreferences;
import com.example.openweather.DataLocal.SharePreferencesManager;
import com.example.openweather.R;
import com.example.openweather.Utils.WeatherUtils;
import com.example.openweather.View.Preference.TimePickerPreference;
import com.example.openweather.View.Preference.TimePickerPreferenceDialog;
import com.example.openweather.ViewModel.Settings.SettingsViewModel;

import dagger.hilt.android.AndroidEntryPoint;
import timber.log.Timber;

@AndroidEntryPoint
public class SettingActivity extends AppCompatActivity {

    private SettingsViewModel settingsViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        settingsViewModel = new ViewModelProvider(this).get(SettingsViewModel.class);
        Toolbar toolbar = findViewById(R.id.tbToolBarOption);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.str_option_menu_setting_title);


        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.settings, new SettingActivity.SettingsFragment(settingsViewModel))
                    .commit();
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    public static class SettingsFragment extends PreferenceFragmentCompat {
        private SettingsViewModel settingsViewModel;
        private ListPreference listPreferenceUnits;
        private SwitchPreferenceCompat swtWeatherService;
        private SwitchPreferenceCompat swtAutoUpdateWeather;
        private TimePickerPreference dgTimePicker;


        public SettingsFragment(SettingsViewModel settingsViewModel) {
           this.settingsViewModel = settingsViewModel;
        }

        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            setPreferencesFromResource(R.xml.pref_setting_screen, rootKey);

            listPreferenceUnits = findPreference("tempUnit");
            swtWeatherService = findPreference("swtWeatherService");
            swtAutoUpdateWeather = findPreference("swtAutoUpdateWeather");
            dgTimePicker = findPreference("dgTimePicker");


            listPreferenceUnits.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                @Override
                public boolean onPreferenceChange(Preference preference, Object newValue) {
                    if(preference instanceof ListPreference){
                        Timber.d("listPreferenceUnits.setOnPreferenceChangeListener");
                        int prefIndex = ((ListPreference) preference).findIndexOfValue(String.valueOf(newValue));
                        if(prefIndex >= 0){
                            settingsViewModel.savePreferenceData(SharePreferencesManager.KEY_PREF_TEMP_UNIT ,newValue);
                        }
                    }
                    return true;
                }
            });
            swtWeatherService.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                @Override
                public boolean onPreferenceChange(Preference preference, Object newValue) {
                    if(preference instanceof SwitchPreferenceCompat){
                        Timber.d("swtWeatherService.setOnPreferenceChangeListener");
                        settingsViewModel.savePreferenceData(SharePreferencesManager.KEY_PREF_STATE_RUN_BG_SERVICE ,newValue);
                    }
                    return true;
                }
            });
            swtAutoUpdateWeather.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                @Override
                public boolean onPreferenceChange(Preference preference, Object newValue) {
                    if(preference instanceof SwitchPreferenceCompat){
                        Timber.d("swtAutoUpdateWeather.setOnPreferenceChangeListener");
                        settingsViewModel.savePreferenceData(SharePreferencesManager.KEY_PREF_STATE_AUTO_UPDATE_WEATHER ,newValue);
                    }
                    return true;
                }
            });
            dgTimePicker.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                @Override
                public boolean onPreferenceChange(Preference preference, Object newValue) {
                    if(preference instanceof TimePickerPreference){
                        Timber.d("dgTimePicker.setOnPreferenceChangeListener");
                        settingsViewModel.savePreferenceData(SharePreferencesManager.KEY_PREF_INTERVAL_AUTO_UPDATE_WEATHER ,newValue);
                    }
                    return true;
                }
            });

        }

        @Override
        public void onDisplayPreferenceDialog(Preference preference) {
            if(preference instanceof TimePickerPreference){
                TimePickerPreferenceDialog timePickerFragment = TimePickerPreferenceDialog.newInstance(preference.getKey());
                timePickerFragment.setTargetFragment(this,0);
                timePickerFragment.show(getParentFragmentManager(),"TimePickerFragment");
            }else{
                super.onDisplayPreferenceDialog(preference);
            }
        }
    }
}
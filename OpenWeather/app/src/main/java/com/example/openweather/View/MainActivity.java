package com.example.openweather.View;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;


import android.content.IntentFilter;
import android.os.Bundle;

import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import android.widget.Toast;

import com.example.openweather.Adapter.WeatherDailyAdapter;
import com.example.openweather.Adapter.WeatherHourlyAdapter;
import com.example.openweather.DataLocal.SharePreferencesManager;
import com.example.openweather.Model.Forecast.Daily;
import com.example.openweather.Model.Forecast.Hourly;
import com.example.openweather.R;
import com.example.openweather.Utils.CoordinateHelper;
import com.example.openweather.Utils.WeatherUtils;
import com.example.openweather.View.Fragment.FragmentDailyWeather;
import com.example.openweather.View.Fragment.FragmentDailyWeatherDetail;
import com.example.openweather.View.SubActivity.SettingActivity;
import com.example.openweather.View.SubActivity.SearchLocationActivity;
import com.example.openweather.View.Fragment.FragCurrentWeather;
import com.example.openweather.ViewModel.MainViewModel;
import com.example.openweather.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import timber.log.Timber;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity {
    private static final String BROADCAST_UPDATE_WEATHER_UNIT_ACTION = "broadcast.update.weather.unit.action";
    private static final String BROADCAST_DATA_WEATHER_UPDATE_UPDATING = "broadcast.data.weather.update.updating";
    private static final String BROADCAST_DATA_WEATHER_UPDATE_DONE = "broadcast.data.weather.update.done";
    private static final String BROADCAST_DATA_WEATHER_UPDATE_ERROR = "broadcast.data.weather.update.error";


    private ActivityMainBinding binding;

    WeatherHourlyAdapter weatherHourlyAdapter;

    MainViewModel mainViewModel;
    BroadcastReceiver broadcastReceiver;

    static private boolean isReCreateActivity;

    @Inject
    SharePreferencesManager sharePreferencesManager;

    @Inject
    CoordinateHelper coordinateHelper;

    @Inject
    WeatherUtils weatherUtils;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        isReCreateActivity=true;

        initView();
        initViewModel();
        initBroadcastReceiver();

        binding.toolBar.btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBtnSearchClicked();
            }
        });
        binding.toolBar.imgMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onImgMenuClicked();
            }
        });
        binding.toolBar.imgLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onImgLocationClicked();
            }
        });


        binding.swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                onSwipeRefreshActived();
            }
        });

        binding.rvListHourly.setNestedScrollingEnabled(false);

    }

    @Override
    protected void onStart() {
        super.onStart();
        if(isReCreateActivity){
            List<String> _coordinate = sharePreferencesManager.getLastestCoordinate();
            if(TextUtils.isEmpty(_coordinate.get(0)) || TextUtils.isEmpty(_coordinate.get(1))){
                mainViewModel.requestWeatherDataByGps();
            }else{
                mainViewModel.requestWeatherDataFromSaveData();
            }
        }
        isReCreateActivity = false;
    }


    void initView(){
        requestShowFragmentCurrentWeather();
        requestShowFragmentDailyWeather();

        binding.rvListHourly.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        weatherHourlyAdapter = new WeatherHourlyAdapter(MainActivity.this,new ArrayList<>(), weatherUtils);
        binding.rvListHourly.setAdapter(weatherHourlyAdapter);

    }

    void initViewModel(){
        mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);

        mainViewModel.tvLocation.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                binding.toolBar.tvLocation.setText(s);
            }
        });

        mainViewModel.listWeatherHourlyLiveData.observe(this, new Observer<List<Hourly>>() {
            @Override
            public void onChanged(List<Hourly> hourlies) {
                weatherHourlyAdapter.setData(hourlies);
            }
        });

    }

    void initBroadcastReceiver(){
        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Timber.d("Broadcast MainActivity : " + intent.getAction());
                if(intent.getAction().equals(BROADCAST_UPDATE_WEATHER_UNIT_ACTION)){
                    weatherHourlyAdapter.notifyDataSetChanged();
                    Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.fragDailyWeather);
                    if (fragment instanceof  FragmentDailyWeather) {
                        ((FragmentDailyWeather)fragment).notifyDataSetChanged();
                    }
                    if (fragment instanceof  FragmentDailyWeatherDetail) {
                        ((FragmentDailyWeatherDetail)fragment).notifyDataSetChanged();
                    }
                }
                if(intent.getAction().equals(BROADCAST_DATA_WEATHER_UPDATE_UPDATING)){
                    if(!binding.swipeRefresh.isRefreshing()){
                        binding.swipeRefresh.setRefreshing(true);
                    }
                }
                if(intent.getAction().equals(BROADCAST_DATA_WEATHER_UPDATE_DONE)){
                    binding.swipeRefresh.setRefreshing(false);
                }
                if(intent.getAction().equals(BROADCAST_DATA_WEATHER_UPDATE_ERROR)){
                    Toast.makeText(MainActivity.this, R.string.str_wrong_connection_short, Toast.LENGTH_SHORT).show();
                    binding.swipeRefresh.setRefreshing(false);
                }
            }
        };

        IntentFilter intentFilter = new IntentFilter(BROADCAST_UPDATE_WEATHER_UNIT_ACTION);
        intentFilter.addAction(BROADCAST_UPDATE_WEATHER_UNIT_ACTION);
        intentFilter.addAction(BROADCAST_DATA_WEATHER_UPDATE_UPDATING);
        intentFilter.addAction(BROADCAST_DATA_WEATHER_UPDATE_DONE);
        intentFilter.addAction(BROADCAST_DATA_WEATHER_UPDATE_ERROR);
        registerReceiver(broadcastReceiver,intentFilter);
    }

    public void requestShowFragmentCurrentWeather(){
        FragmentTransaction transaction;
        transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragCurrentWeather, new FragCurrentWeather());
        transaction.commit();
    }
    public void requestShowFragmentDailyWeather(){
        FragmentTransaction transaction;
        transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragDailyWeather, new FragmentDailyWeather());
        transaction.commit();
    }
    public void requestShowFragmentDailyWeatherDetail(int index){
        FragmentTransaction transaction;
        transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragDailyWeather, FragmentDailyWeatherDetail.newInstance(index));
        transaction.commit();
    }

    ActivityResultLauncher<Intent> startActivityResult = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        // There are no request codes
                        Intent data = result.getData();
                        String location = data.getStringExtra("location");
                        String lat = data.getStringExtra("lat");
                        String lon = data.getStringExtra("lon");
                        mainViewModel.saveLocationData(lat,lon,location,1);
                        mainViewModel.requestWeatherDataFromSaveData();
                    }
                }
            });


    void onBtnSearchClicked(){
        Intent intent = new Intent(this, SearchLocationActivity.class);
        startActivityResult.launch(intent);
    }

    void onImgMenuClicked(){
        Intent intent = new Intent(this, SettingActivity.class);
        startActivity(intent);
    }

    void onImgLocationClicked(){
        mainViewModel.requestWeatherDataByGps();
    }

    void onSwipeRefreshActived(){
        mainViewModel.requestWeatherDataFromSaveData();
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        super.dispatchTouchEvent(event);

        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN: // Mouse Pressed
            case MotionEvent.ACTION_UP:  // Mouse Released
                if(binding.swipeRefresh.getChildAt(0).getScrollY() == 0){
                    binding.swipeRefresh.setEnabled(true);
                }else{
                    binding.swipeRefresh.setEnabled(false);
                }
                break;
            case MotionEvent.ACTION_MOVE: // Mouse Move
                break;
        }
        return false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(broadcastReceiver);
    }
}
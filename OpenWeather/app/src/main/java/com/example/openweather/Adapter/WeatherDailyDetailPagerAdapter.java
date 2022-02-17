package com.example.openweather.Adapter;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.openweather.Model.Forecast.Daily;
import com.example.openweather.View.Fragment.FragmentWeatherDailyDetailPager;

import java.util.List;

public class WeatherDailyDetailPagerAdapter extends FragmentStateAdapter {

    private List<Daily> listDailyWeather;

    public WeatherDailyDetailPagerAdapter(@NonNull FragmentActivity fragmentActivity, List<Daily> listDailyWeather) {
        super(fragmentActivity);
        this.listDailyWeather = listDailyWeather;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        Log.d("TAG", "createFragment: " + position);
        return FragmentWeatherDailyDetailPager.newInstance(position);
    }

    @Override
    public int getItemCount() {
        return listDailyWeather.size();
    }

}

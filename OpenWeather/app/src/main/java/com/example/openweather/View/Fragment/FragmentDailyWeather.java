package com.example.openweather.View.Fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.openweather.Adapter.WeatherDailyAdapter;
import com.example.openweather.Model.Forecast.Daily;
import com.example.openweather.R;
import com.example.openweather.Utils.WeatherUtils;
import com.example.openweather.View.MainActivity;
import com.example.openweather.ViewModel.MainViewModel;
import com.example.openweather.databinding.FragmentCurrentWeatherBinding;
import com.example.openweather.databinding.FragmentDailyWeatherBinding;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import timber.log.Timber;


@AndroidEntryPoint
public class FragmentDailyWeather extends Fragment {
    private FragmentDailyWeatherBinding binding;
    private MainViewModel mainViewModel;
    private WeatherDailyAdapter weatherDailyAdapter;

    @Inject
    WeatherUtils weatherUtils;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentDailyWeatherBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
        initViewModel();
    }

    private void initView(){
        binding.rvListDaily.setNestedScrollingEnabled(false);
        binding.rvListDaily.setLayoutManager(new LinearLayoutManager(getActivity()));
        weatherDailyAdapter = new WeatherDailyAdapter(requireActivity(), new ArrayList<>(), weatherUtils, new WeatherDailyAdapter.ItemClickListener() {
            @Override
            public void onItemClicked(int index) {
                ((MainActivity)getActivity()).requestShowFragmentDailyWeatherDetail(index);
            }
        });
        binding.rvListDaily.setAdapter(weatherDailyAdapter);
    }

    private void initViewModel(){
        mainViewModel = new ViewModelProvider(getActivity()).get(MainViewModel.class);
        mainViewModel.listWeatherDailyLiveData.observe(getActivity(), new Observer<List<Daily>>() {
            @Override
            public void onChanged(List<Daily> listDailyWeather) {
                weatherDailyAdapter.setData(listDailyWeather);
            }
        });
    }

    public void notifyDataSetChanged(){
        weatherDailyAdapter.notifyDataSetChanged();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
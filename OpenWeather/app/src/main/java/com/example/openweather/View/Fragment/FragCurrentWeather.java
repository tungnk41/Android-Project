package com.example.openweather.View.Fragment;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.openweather.ViewModel.MainViewModel;
import com.example.openweather.databinding.FragmentCurrentWeatherBinding;

import dagger.hilt.android.AndroidEntryPoint;


@AndroidEntryPoint
public class FragCurrentWeather extends Fragment {

    private FragmentCurrentWeatherBinding binding;
    private MainViewModel mainViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.frag_current_weather_fragment, container, false);
        binding = FragmentCurrentWeatherBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mainViewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);
        createObserverToModel();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


    void createObserverToModel(){

        mainViewModel.tvLocation.observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                //This name is update with trigger Preference
            }
        });

        mainViewModel.imgWeatherIcon.observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                fetchImage(s);
            }
        });

        mainViewModel.tvWeatherCondition.observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                binding.tvWeatherCondition.setText(s);
            }
        });

        mainViewModel.tvDescription.observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                binding.tvDescription.setText(s);
            }
        });

        mainViewModel.tvTemperature.observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                binding.tvTemperature.setText(s);
            }
        });

        mainViewModel.tvFeelsLike.observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                binding.tvFeelsLike.setText(s);
            }
        });

        /******************************************************************************************/



        mainViewModel.tvWindDetailSpeed.observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String windSpeed) {
                binding.tvWeatherDetail.tvWindDetailSpeed.setText(windSpeed);
            }
        });

        mainViewModel.tvHumidityDetail.observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String humidity) {
                binding.tvWeatherDetail.tvHumidityDetail.setText(humidity);
            }
        });

        mainViewModel.tvClouds.observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String clouds) {
                binding.tvWeatherDetail.tvClouds.setText(clouds);
            }
        });


        mainViewModel.tvPressureDetail.observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String pressure) {
                binding.tvWeatherDetail.tvPressureDetail.setText(pressure);
            }
        });


        mainViewModel.tvSunRise.observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String sunRise) {
                binding.tvWeatherDetail.tvSunRise.setText(sunRise);
            }
        });

        mainViewModel.tvSunSet.observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String sunSet) {
                binding.tvWeatherDetail.tvSunSet.setText(sunSet);
            }
        });

        mainViewModel.tvUpdateTime.observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                binding.tvUpdateTime.setText(s);
            }
        });

    }

    /***********************************************************************************************/


    private void fetchImage(String image){
        final String url = "https://openweathermap.org/img/wn/" + image + "@2x.png";
        Glide.with(this)
                .load(url)
                .into(binding.imgWeatherIcon);
    }

}
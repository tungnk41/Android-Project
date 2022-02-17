package com.example.openweather.View.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.openweather.Adapter.WeatherDailyDetailPagerAdapter;
import com.example.openweather.Adapter.WeatherDayDateAdapter;
import com.example.openweather.Model.Forecast.Daily;
import com.example.openweather.Model.WeatherDayDate;
import com.example.openweather.Utils.WeatherUtils;
import com.example.openweather.View.MainActivity;
import com.example.openweather.ViewModel.MainViewModel;
import com.example.openweather.databinding.FragmentDailyWeatherDetailBinding;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import timber.log.Timber;

@AndroidEntryPoint
public class FragmentDailyWeatherDetail extends Fragment {
    private static final String ARG_PARAM1 = "param1";

    private FragmentDailyWeatherDetailBinding binding;
    private MainViewModel mainViewModel;
    private WeatherDayDateAdapter weatherDayDateAdapter;
    private List<Daily> listDailyWeather;
    private WeatherDailyDetailPagerAdapter pagerAdapter;


    private Integer index;

    @Inject
    WeatherUtils weatherUtils;

    public FragmentDailyWeatherDetail() {
        // Required empty public constructor
    }


    public static FragmentDailyWeatherDetail newInstance(Integer index) {
        FragmentDailyWeatherDetail fragment = new FragmentDailyWeatherDetail();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, index);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            index = getArguments().getInt(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentDailyWeatherDetailBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViewModel();
        initView();
        initData();
        weatherDayDateAdapter.setSelectedItem(index);
        binding.vpPager.setCurrentItem(index,false);
    }

    private void initView(){
        binding.rvDayDate.setNestedScrollingEnabled(false);
        binding.rvDayDate.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false));

        binding.vpPager.setNestedScrollingEnabled(false);
        pagerAdapter = new WeatherDailyDetailPagerAdapter(getActivity(), listDailyWeather);
        binding.vpPager.setAdapter(pagerAdapter);

        weatherDayDateAdapter = new WeatherDayDateAdapter(new ArrayList<>(), new WeatherDayDateAdapter.ItemClickListener() {
            @Override
            public void onItemClicked(int index) {
                updateCurrentPosition(index);
                binding.vpPager.setCurrentItem(index);
            }
        });
        binding.rvDayDate.setAdapter(weatherDayDateAdapter);
    }

    private void initViewModel(){
        mainViewModel = new ViewModelProvider(getActivity()).get(MainViewModel.class);
        mainViewModel.listWeatherDailyLiveData.observe(getActivity(), new Observer<List<Daily>>() {
            @Override
            public void onChanged(List<Daily> dailies) {
                listDailyWeather = dailies;
                notifyDataSetChanged();
            }
        });
    }

    private void initData(){
        initRvDayDate(listDailyWeather);
        binding.imageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity)getActivity()).requestShowFragmentDailyWeather();
            }
        });

        binding.vpPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                updateCurrentPosition(position);
                weatherDayDateAdapter.setSelectedItem(position);
            }
        });
    }


    void initRvDayDate(List<Daily> listDailyWeather){
        List<WeatherDayDate> listWeatherDayDate = new ArrayList<>();
        for (Daily dailyWeather: listDailyWeather) {
            listWeatherDayDate.add(new WeatherDayDate(weatherUtils.dayFormatter(dailyWeather.getDt()),weatherUtils.dateFormatter(dailyWeather.getDt())));
        }
        weatherDayDateAdapter.setData(listWeatherDayDate);
    }


    public void notifyDataSetChanged(){
        if(listDailyWeather != null && weatherDayDateAdapter != null && getActivity() != null){
            pagerAdapter = new WeatherDailyDetailPagerAdapter(getActivity(), listDailyWeather);
            binding.vpPager.setAdapter(pagerAdapter);
            weatherDayDateAdapter.setSelectedItem(index);
            binding.vpPager.setCurrentItem(index,false);
        }
    }

    void updateCurrentPosition(int position){
        if(this.index != position){
            this.index = position;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding.vpPager.unregisterOnPageChangeCallback(null);
    }
}
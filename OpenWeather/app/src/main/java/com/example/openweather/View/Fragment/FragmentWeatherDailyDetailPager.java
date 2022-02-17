package com.example.openweather.View.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.openweather.Adapter.WeatherDailyDetaiListInforAdapter;
import com.example.openweather.Model.Forecast.Daily;
import com.example.openweather.Model.WeatherDailyDetail;
import com.example.openweather.R;
import com.example.openweather.Utils.WeatherUtils;
import com.example.openweather.ViewModel.MainViewModel;
import com.example.openweather.databinding.FragmentWeatherDailyDetailPagerBinding;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class FragmentWeatherDailyDetailPager extends Fragment {

    private static final String ARG_PARAM1 = "param1";

    private FragmentWeatherDailyDetailPagerBinding binding;
    private MainViewModel mainViewModel;
    private WeatherDailyDetaiListInforAdapter weatherDailyDetaiListInforAdapter;
    private List<Daily> listDailyWeather;

    @Inject
    WeatherUtils weatherUtils;

    private Integer index;

    public FragmentWeatherDailyDetailPager() {
        // Required empty public constructor
    }

    public static FragmentWeatherDailyDetailPager newInstance(int param1) {
        FragmentWeatherDailyDetailPager fragment = new FragmentWeatherDailyDetailPager();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, param1);
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
        binding = FragmentWeatherDailyDetailPagerBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
        initViewModel();
        initData();
    }

    void initView(){
        binding.rvListDailyDetail.setNestedScrollingEnabled(false);
        binding.rvListDailyDetail.setLayoutManager(new LinearLayoutManager(requireActivity()));

        weatherDailyDetaiListInforAdapter = new WeatherDailyDetaiListInforAdapter(new ArrayList<>());
        binding.rvListDailyDetail.setAdapter(weatherDailyDetaiListInforAdapter);
    }

    void initViewModel(){
        mainViewModel = new ViewModelProvider(getActivity()).get(MainViewModel.class);
    }

    void initData(){
        listDailyWeather = mainViewModel.listWeatherDailyLiveData.getValue();
        initWeatherDetailData(listDailyWeather, index);
    }

    void initWeatherDetailData(List<Daily> listDailyWeather, int index){
        this.index = index;
        Daily dailyWeather = listDailyWeather.get(index);
        List<WeatherDailyDetail> listWeatherDailyDetail = new ArrayList<>();
        listWeatherDailyDetail.add(new WeatherDailyDetail(getStringResource(R.string.str_weather_detail_wind_speed) ,proxyTvWindDetailSpeed(dailyWeather.getWindSpeed())));
        listWeatherDailyDetail.add(new WeatherDailyDetail(getStringResource(R.string.str_weather_detail_humidity),proxyTvHumidityDetail(dailyWeather.getHumidity())));
        listWeatherDailyDetail.add(new WeatherDailyDetail(getStringResource(R.string.str_weather_detail_pressure),proxyTvPressureDetail(dailyWeather.getPressure())));
        listWeatherDailyDetail.add(new WeatherDailyDetail(getStringResource(R.string.str_weather_detail_sunrise),proxyTvSunRise(dailyWeather.getSunrise())));
        listWeatherDailyDetail.add(new WeatherDailyDetail(getStringResource(R.string.str_weather_detail_sunset),proxyTvSunSet(dailyWeather.getSunset())));
        weatherDailyDetaiListInforAdapter.setData(listWeatherDailyDetail);

        String _weatherMain = dailyWeather.getListWeather().get(0).getMain();
        String _weatherCondition = dailyWeather.getListWeather().get(0).getDescription();
        String _tempMax = weatherUtils.temperatureUtil((int)dailyWeather.getTemperatureDaily().getTempMax());
        String _tempMin = weatherUtils.temperatureUtil((int)dailyWeather.getTemperatureDaily().getTempMin());
        String _imgWeather = dailyWeather.getListWeather().get(0).getIcon();

        String url = "https://openweathermap.org/img/wn/" + _imgWeather + "@2x.png";
        Glide.with(requireActivity())
                .load(url)
                .into(binding.imgWeather);

        binding.tvTempMax.setText(_tempMax);
        binding.tvTempMin.setText(_tempMin);
        binding.tvMainWeather.setText(_weatherMain);
        binding.tvWeatherCondition.setText(_weatherCondition);
    }

    String proxyTvWindDetailSpeed(double speed){
        return  speed + " " + getStringResource(R.string.str_weather_detail_wind_speed_unit);
    }
    String proxyTvHumidityDetail(int humidity){
        return  humidity + " " + getStringResource(R.string.str_weather_detail_humidity_unit);
    }
    String proxyTvPressureDetail(int pressure){
        return  pressure + " " + getStringResource(R.string.str_weather_detail_pressure_unit);
    }
    String proxyTvSunRise(long sunRise){
        return weatherUtils.timeFormatter(sunRise);
    }
    String proxyTvSunSet(long sunSet){
        return weatherUtils.timeFormatter(sunSet);
    }

    private String getStringResource(int id){
        return getActivity().getResources().getString(id);
    }
}
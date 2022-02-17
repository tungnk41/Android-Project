package com.example.openweather.Service;

import com.example.openweather.Model.CurrentWeather.CurrentWeather;
import com.example.openweather.Model.Forecast.Forecast;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

public interface WeatherServiceApi {
    //https://api.openweathermap.org/data/2.5/weather?q=HaNoi&appid=3b4d3ad9dac72a8da6c42eb87d2aecb1

    /*
    * location : HaNoi
    * units : metric (Celsius) , imperial (Fahrenheit), default: kevin
    * lang : vi, en
    * */
    @GET("weather")
    Call<CurrentWeather> getCurrentWeatherByLocation(@QueryMap Map<String,String> options);

    @GET("weather")
    Call<CurrentWeather> getCurrentWeatherByCoordinate(@QueryMap Map<String,String> options);

    @GET("onecall")
    Call<Forecast> getForecastWeatherByCoordinate(@QueryMap Map<String,String> options);


}

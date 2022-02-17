package com.example.openweather.Repository;


import com.example.openweather.Model.CurrentWeather.CurrentWeather;
import com.example.openweather.Model.Forecast.Forecast;
import com.example.openweather.Model.Location.Location;

import java.util.List;

public interface Repository {

    interface ResultSearchLocation{
        void onSuccess(List<Location> listSearchLocation);
        void onFailure(Throwable t);
    }

    interface ResultCurrentWeather{
        void onSuccess(CurrentWeather currentWeather);
        void onFailure(Throwable t);
    }

    interface ResultForecastWeather{
        void onSuccess(Forecast forecast);
        void onFailure(Throwable t);
    }

    // For Fetch Data
    void searchLocation(String location,ResultSearchLocation result);
    void getCurrentWeatherByLocation(String location,ResultCurrentWeather result);
    void getCurrentWeatherByCoordinate(String latitude,String longitude,ResultCurrentWeather result);
    void getForecastWeatherByCoordinate( String latitude,String longitude,ResultForecastWeather result);
}

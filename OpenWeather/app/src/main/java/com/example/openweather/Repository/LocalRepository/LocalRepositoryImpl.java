package com.example.openweather.Repository.LocalRepository;

import com.example.openweather.DataLocal.LocationDatabase.LocationDAO;
import com.example.openweather.DataLocal.LocationDatabase.Database;
import com.example.openweather.Model.Location.Location;
import com.example.openweather.Repository.Repository;
import com.example.openweather.Utils.ThreadPoolHelper;

import java.util.List;

import javax.inject.Inject;

public class LocalRepositoryImpl implements Repository {
    private LocationDAO locationDAO;
    private ThreadPoolHelper threadPoolHelper;

    @Inject
    public LocalRepositoryImpl(Database _Database, ThreadPoolHelper _threadPoolHelper) {
        this.locationDAO = _Database.locationDAO;
        this.threadPoolHelper = _threadPoolHelper;
    }


    @Override
    public void searchLocation(String location, ResultSearchLocation result) {
        threadPoolHelper.runThreadPoolExecutor(() -> {
            try {
                List<Location> list = locationDAO.searchLocation(location);
                result.onSuccess(list);
            }catch (Exception e){
                result.onFailure(e);
            }
        });
    }

    @Override
    public void getCurrentWeatherByLocation(String location, ResultCurrentWeather result) {

    }

    @Override
    public void getCurrentWeatherByCoordinate(String latitude, String longitude, ResultCurrentWeather result) {

    }

    @Override
    public void getForecastWeatherByCoordinate(String latitude, String longitude, ResultForecastWeather result) {

    }
}

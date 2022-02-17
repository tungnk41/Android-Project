package com.example.openweather.Repository.RemoteRepository;

import android.content.Context;

import com.example.openweather.Model.CurrentWeather.CurrentWeather;
import com.example.openweather.Model.Forecast.Forecast;
import com.example.openweather.Model.Location.Location;
import com.example.openweather.R;
import com.example.openweather.Repository.Repository;
import com.example.openweather.Service.LocationServiceApi;
import com.example.openweather.Service.WeatherServiceApi;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import dagger.hilt.android.qualifiers.ApplicationContext;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

public class RemoteRepositoryImpl implements Repository {

    private WeatherServiceApi weatherServiceApi;
    private LocationServiceApi locationServiceApi;
    private Context context;

    @Inject
    public RemoteRepositoryImpl(@ApplicationContext Context context, WeatherServiceApi _weatherServiceApi, LocationServiceApi _locationServiceApi) {
        this.weatherServiceApi = _weatherServiceApi;
        this.locationServiceApi = _locationServiceApi;
        this.context = context;
    }


    public void searchLocation(String location, Repository.ResultSearchLocation result){
        String api_key = getStringResource(R.string.api_key);
        Map<String,String> options = new HashMap<>();
        options.put("q",location);
        options.put("limit","10");
        options.put("appid",api_key);
        locationServiceApi.getListSearchLocationByLocation(options)
                .enqueue(new Callback<List<Location>>() {
                    @Override
                    public void onResponse(Call<List<Location>> call, Response<List<Location>> response) {
                        List<Location> _listSearchLocations = response.body();
                        result.onSuccess(_listSearchLocations);
                        Timber.d("onResponse: searchLocationApi Successful");
                    }
                    @Override
                    public void onFailure(Call<List<Location>> call, Throwable t) {
                        result.onFailure(t);
                        Timber.d( "onFailure: searchLocationApi Failed");
                    }
                });
    }

    /*******************************************************************/

    public void getCurrentWeatherByLocation(String location, Repository.ResultCurrentWeather result) {
        String api_key = getStringResource(R.string.api_key);
        Map<String,String> options = new HashMap<>();
        options.put("q",location);
        options.put("units","metric");
        options.put("lang","en");
        options.put("appid",api_key);
        weatherServiceApi.getCurrentWeatherByLocation(options)
                .enqueue(new Callback<CurrentWeather>() {
                    @Override
                    public void onResponse(Call<CurrentWeather> call, Response<CurrentWeather> response) {
                        CurrentWeather _currentWeather = response.body();
                        result.onSuccess(_currentWeather);
                        Timber.d( "onResponse: getCurrentWeatherByLocationApi Successful");
                    }

                    @Override
                    public void onFailure(Call<CurrentWeather> call, Throwable t) {
                        result.onFailure(t);
                        Timber.d( "onFailure: getCurrentWeatherByLocationApi Failed");
                    }
                });
    }


    /*******************************************************************/

    public void getCurrentWeatherByCoordinate(String latitude,String longitude, Repository.ResultCurrentWeather result) {
        String api_key = getStringResource(R.string.api_key);
        Map<String,String> options = new HashMap<>();
        options.put("lat",latitude);
        options.put("lon",longitude);
        options.put("units","metric");
        options.put("lang","en");
        options.put("appid",api_key);
        weatherServiceApi.getCurrentWeatherByCoordinate(options)
                .enqueue(new Callback<CurrentWeather>() {
                    @Override
                    public void onResponse(Call<CurrentWeather> call, Response<CurrentWeather> response) {
                        CurrentWeather _currentWeather = response.body();
                        result.onSuccess(_currentWeather);
                        Timber.d( "onResponse: getCurrentWeatherByCoordinateApi Successful");
                    }

                    @Override
                    public void onFailure(Call<CurrentWeather> call, Throwable t) {
                        result.onFailure(t);
                        Timber.d( "onFailure: getCurrentWeatherByCoordinateApi Failed");
                    }
                });
    }


    public void getForecastWeatherByCoordinate( String latitude,String longitude, Repository.ResultForecastWeather result) {
        String api_key = getStringResource(R.string.api_key);
        Map<String,String> options = new HashMap<>();
        options.put("lat",latitude);
        options.put("lon",longitude);
        options.put("units","metric");
        options.put("lang","en");
        options.put("exclude","current,minutely");
        options.put("appid",api_key);

        weatherServiceApi.getForecastWeatherByCoordinate(options)
                .enqueue(new Callback<Forecast>() {
                    @Override
                    public void onResponse(Call<Forecast> call, Response<Forecast> response) {
                        Forecast _forecastWeather = response.body();
                        result.onSuccess(_forecastWeather);
                        Timber.d( "onResponse: getForecastWeatherByCoordinateApi Successful");
                    }

                    @Override
                    public void onFailure(Call<Forecast> call, Throwable t) {
                        result.onFailure(t);
                        Timber.d( "onFailure: getForecastWeatherByCoordinateApi Failed");
                    }
                });
    }

    private String getStringResource(int id) {
        return context.getResources().getString(id);
    }


}

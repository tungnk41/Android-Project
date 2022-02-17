package com.example.openweather.Service;

import com.example.openweather.Model.Location.Location;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;
import java.util.Map;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

public interface LocationServiceApi {

    @GET("direct")
    Call<List<Location>> getListSearchLocationByLocation(@QueryMap Map<String,String> options);
}

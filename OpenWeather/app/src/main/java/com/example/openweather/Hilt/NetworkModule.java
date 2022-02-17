package com.example.openweather.Hilt;

import android.content.Context;

import com.example.openweather.Service.LocationServiceApi;
import com.example.openweather.Service.WeatherServiceApi;
import com.example.openweather.Utils.NetworkHelper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
@InstallIn(SingletonComponent.class)
public class NetworkModule {

    @Provides
    @Singleton
    HttpLoggingInterceptor provideHttpLoggingInterceptor(){
        return new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);
    }

    @Provides
    @Singleton
    Gson provideGson(){
        return new GsonBuilder()
                .setDateFormat("YYYY-MM-dd HH:mm:ss")
                .create();
    }

    @Provides
    @Singleton
    OkHttpClient provideOkHttpClient(HttpLoggingInterceptor httpLoggingInterceptor){
        return new OkHttpClient.Builder()
                .addInterceptor(httpLoggingInterceptor)
                .build();
    }

    @Provides
    @Singleton
    Retrofit.Builder provideRetrofitBuilder(Gson gson, OkHttpClient okHttpClient){
        return new Retrofit.Builder()
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson));
    }


    /*************************************************************************************/


    @Provides
    @Singleton
    WeatherServiceApi provideWeatherService(Retrofit.Builder retrofitBuilder){
        return retrofitBuilder
                .baseUrl("https://api.openweathermap.org/data/2.5/")
                .build()
                .create(WeatherServiceApi.class);
    }

    @Provides
    @Singleton
    LocationServiceApi provideLocationAPIService(Retrofit.Builder retrofitBuilder){
        return retrofitBuilder
                .baseUrl("http://api.openweathermap.org/geo/1.0/")
                .build()
                .create(LocationServiceApi.class);
    }

    @Provides
    @Singleton
    NetworkHelper provideNetworkHelper(@ApplicationContext Context context){
        return new NetworkHelper(context);
    }
}

package com.example.openweather.Model.Forecast;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Hourly {

    @SerializedName("dt")
    private int time;
    @SerializedName("temp")
    private double temperature;
    @SerializedName("weather")
    private List<Weather> listWeather;

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public List<Weather> getListWeather() {
        return listWeather;
    }

    public void setListWeather(List<Weather> listWeather) {
        this.listWeather = listWeather;
    }

    @Override
    public String toString() {
        return "Hourly{" +
                "time=" + time +
                ", temperature=" + temperature +
                ", listWeather=" + listWeather +
                '}';
    }
}

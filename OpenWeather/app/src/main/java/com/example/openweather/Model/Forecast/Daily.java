package com.example.openweather.Model.Forecast;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Daily {
    private int dt;
    @SerializedName("temp")
    private TemperatureDaily temperatureDaily;
    @SerializedName("weather")
    private List<Weather> listWeather;
    private int pressure;
    private int humidity;
    private long sunrise;
    private long sunset;
    @SerializedName("wind_speed")
    private double windSpeed;

    public int getDt() {
        return dt;
    }

    public void setDt(int dt) {
        this.dt = dt;
    }

    public TemperatureDaily getTemperatureDaily() {
        return temperatureDaily;
    }

    public void setTemperatureDaily(TemperatureDaily temperatureDaily) {
        this.temperatureDaily = temperatureDaily;
    }

    public List<Weather> getListWeather() {
        return listWeather;
    }

    public void setListWeather(List<Weather> listWeather) {
        this.listWeather = listWeather;
    }

    public int getPressure() {
        return pressure;
    }

    public void setPressure(int pressure) {
        this.pressure = pressure;
    }

    public int getHumidity() {
        return humidity;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }

    public long getSunrise() {
        return sunrise;
    }

    public void setSunrise(long sunrise) {
        this.sunrise = sunrise;
    }

    public long getSunset() {
        return sunset;
    }

    public void setSunset(long sunset) {
        this.sunset = sunset;
    }

    public double getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(double windSpeed) {
        this.windSpeed = windSpeed;
    }

    @Override
    public String toString() {
        return "Daily{" +
                "dt=" + dt +
                ", temperatureDaily=" + temperatureDaily +
                ", listWeather=" + listWeather +
                '}';
    }
}

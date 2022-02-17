package com.example.openweather.Model.CurrentWeather;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CurrentWeather {
    @SerializedName("weather")
    private List<Weather> listWeather;
    private Main main;
    private Wind wind;
    private Sys sys;
    private String name;
    private Coord coord;
    private Clouds clouds;


    public List<Weather> getListWeather() {
        return listWeather;
    }

    public void setListWeather(List<Weather> listWeather) {
        this.listWeather = listWeather;
    }

    public Main getMain() {
        return main;
    }

    public void setMain(Main main) {
        this.main = main;
    }

    public Wind getWind() {
        return wind;
    }

    public void setWind(Wind wind) {
        this.wind = wind;
    }

    public Sys getSys() {
        return sys;
    }

    public void setSys(Sys sys) {
        this.sys = sys;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Coord getCoord() {
        return coord;
    }

    public void setCoord(Coord coord) {
        this.coord = coord;
    }

    public Clouds getClouds() {
        return clouds;
    }

    public void setClouds(Clouds clouds) {
        this.clouds = clouds;
    }

    @Override
    public String toString() {
        return "CurrentWeather{" +
                "listWeather=" + listWeather +
                ", main=" + main +
                ", wind=" + wind +
                ", sys=" + sys +
                ", name='" + name + '\'' +
                ", coord=" + coord +
                ", clouds=" + clouds +
                '}';
    }
}

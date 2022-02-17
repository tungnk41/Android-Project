package com.example.openweather.Model.Forecast;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Forecast {

    @SerializedName("hourly")
    private List<Hourly> listHourly;

    @SerializedName("daily")
    private List<Daily> listDaily;

    public List<Hourly> getListHourly() {
        return listHourly;
    }

    public void setListHourly(List<Hourly> listHourly) {
        this.listHourly = listHourly;
    }

    public List<Daily> getListDaily() {
        return listDaily;
    }

    public void setListDaily(List<Daily> listDaily) {
        this.listDaily = listDaily;
    }

    @Override
    public String toString() {
        return "Forecast{" +
                "listHourly=" + listHourly +
                ", listDaily=" + listDaily +
                '}';
    }
}

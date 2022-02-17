package com.example.openweather.Model.Forecast;

import com.google.gson.annotations.SerializedName;

public class TemperatureDaily {
    @SerializedName("max")
    private double tempMax;
    @SerializedName("min")
    private double tempMin;

    public double getTempMax() {
        return tempMax;
    }

    public void setTempMax(double tempMax) {
        this.tempMax = tempMax;
    }

    public double getTempMin() {
        return tempMin;
    }

    public void setTempMin(double tempMin) {
        this.tempMin = tempMin;
    }

    @Override
    public String toString() {
        return "TemperatureDaily{" +
                "tempMax=" + tempMax +
                ", tempMin=" + tempMin +
                '}';
    }
}

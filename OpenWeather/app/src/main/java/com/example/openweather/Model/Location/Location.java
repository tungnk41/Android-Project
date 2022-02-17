package com.example.openweather.Model.Location;


import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Location implements Serializable {
    @SerializedName("name")
    private String city;
    private double lat;
    private double lon;
    private String country;
    @SerializedName("local_names")
    private LocalNames localNames;

    public Location(String localNames, String country) {
        this.localNames = new LocalNames(localNames);
        this.country = country;
    }

    public Location() {

    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public LocalNames getLocalNames() {
        return localNames;
    }

    public void setLocalNames(LocalNames localNames) {
        this.localNames = localNames;
    }

    @Override
    public String toString() {
        return "LocationAPI{" +
                "name='" + city + '\'' +
                ", lat=" + lat +
                ", lon=" + lon +
                ", country='" + country + '\'' +
                ", localNames=" + localNames +
                '}';
    }
}

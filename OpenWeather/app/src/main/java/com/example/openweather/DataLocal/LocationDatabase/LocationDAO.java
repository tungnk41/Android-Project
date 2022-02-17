package com.example.openweather.DataLocal.LocationDatabase;

import com.example.openweather.Model.Location.Location;

import java.util.List;

public interface LocationDAO {
    List<Location> getAllLocationData();
    List<Location> searchLocation(String location);
}

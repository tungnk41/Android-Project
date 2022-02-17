package com.example.openweather.Utils;


import android.content.Context;


import com.example.openweather.DataLocal.SharePreferencesManager;
import com.example.openweather.Service.GpsTrackerService;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.qualifiers.ApplicationContext;

public class CoordinateHelper {
    private SharePreferencesManager sharePreferencesManager;
    private Context context;

    @Inject
    public CoordinateHelper(@ApplicationContext Context context, SharePreferencesManager sharePreferencesManager){
        this.sharePreferencesManager = sharePreferencesManager;
        this.context = context;
    }

    public List<String> getCurrentCoordinateByGps(){
        List<String> result = new ArrayList<>(); //Contain lat,lon value
        double latitude = 0;
        double longitude = 0;
        GpsTrackerService gpsTrackerService = new GpsTrackerService(context);
        if(gpsTrackerService.isLocationAvailable()){
            latitude = gpsTrackerService.getLatitude();
            longitude = gpsTrackerService.getLongitude();
        }else{
            gpsTrackerService.showSettingsAlert();
        }
        result.add(String.valueOf(latitude));
        result.add(String.valueOf(longitude));
        sharePreferencesManager.putLastestCoordinate(String.valueOf(latitude),String.valueOf(longitude));
        return result;
    }
}

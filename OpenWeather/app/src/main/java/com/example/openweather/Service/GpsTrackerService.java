package com.example.openweather.Service;

import android.Manifest;
import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.IBinder;
import android.provider.Settings;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;

import dagger.hilt.android.qualifiers.ApplicationContext;
import timber.log.Timber;


public class GpsTrackerService extends Service implements LocationListener {
    private static final String TAG = "GpsTracker";
    // The minimum distance to change Updates in meters
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10; // 10 meters
    // The minimum time between updates in milliseconds
    private static final long MIN_TIME_BETWEEN_UPDATES = 1000 * 60 * 1; // 1 minute


    private Context context;
    private boolean isGpsEnabled = false;
    private boolean isNetworkEnabled = false;
    private boolean isLocationAvailable = false;

    private Location location;
    private double latitude;
    private double longitude;

    private LocationManager locationManager;

    public GpsTrackerService(@ApplicationContext Context context){
        this.context = context;
        getLocation();
    }


    public Location getLocation(){
        locationManager = (LocationManager) context.getSystemService(LOCATION_SERVICE);
        isGpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        if(!isGpsEnabled && !isNetworkEnabled){
            Timber.d( "getLocation: Failed");
        }else{

            isLocationAvailable = true;
            if (isNetworkEnabled) {
                getLocationFromNetwork();
            }
            // if GPS Enabled and Can not get Location from network, get lat/long using GPS Services
            if (isGpsEnabled) {
                getLocationFromGps();
            }
        }

        return location;
    }


    private void getLocationFromNetwork(){
        //check the network permission
        if (ActivityCompat.checkSelfPermission(context,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(context,
                        Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions((Activity) context,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION}, 101);
        }
        locationManager.requestLocationUpdates(
                LocationManager.NETWORK_PROVIDER,
                MIN_TIME_BETWEEN_UPDATES,
                MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
        Timber.d( "Network Enabled");
        if (locationManager != null) {
            location = locationManager
                    .getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

            if (location != null) {
                latitude = location.getLatitude();
                longitude = location.getLongitude();
            }
        }
    }

    private void getLocationFromGps(){
        if (location == null) {
            //check the network permission
            if (ActivityCompat.checkSelfPermission(context,
                    Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(context,
                            Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions((Activity) context,
                        new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION,
                                Manifest.permission.ACCESS_COARSE_LOCATION}, 101);
            }
            locationManager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER,
                    MIN_TIME_BETWEEN_UPDATES,
                    MIN_DISTANCE_CHANGE_FOR_UPDATES, this);

            Timber.d( "GPS Enabled");
            if (locationManager != null) {
                location = locationManager
                        .getLastKnownLocation(LocationManager.GPS_PROVIDER);

                if (location != null) {
                    latitude = location.getLatitude();
                    longitude = location.getLongitude();
                }
            }
        }
    }

    public void stopUsingGps(){
        if(locationManager != null){
            locationManager.removeUpdates(GpsTrackerService.this);
        }
    }

    public double getLatitude(){
        if(location != null){
            latitude = location.getLatitude();
        }
        return latitude;
    }

    public double getLongitude() {
        if(location != null){
            longitude = location.getLongitude();
        }
        return longitude;
    }

    public boolean isLocationAvailable(){
        return isLocationAvailable;
    }



    //Dialog for show setting location
    public void showSettingsAlert(){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);

        // Setting Dialog Title
        alertDialog.setTitle("GPS is settings");

        // Setting Dialog Message
        alertDialog.setMessage("GPS is not enabled. Do you want to go to settings menu?");

        // On pressing Settings button
        alertDialog.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int which) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                context.startActivity(intent);
            }
        });

        // on pressing cancel button
        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        alertDialog.show();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {

    }
}

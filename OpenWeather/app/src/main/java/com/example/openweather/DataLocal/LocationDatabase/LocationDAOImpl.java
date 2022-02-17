package com.example.openweather.DataLocal.LocationDatabase;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.openweather.Model.Location.Location;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class LocationDAOImpl implements LocationDAO {
    public static final String TABLE_NAME = "location";
    public static final String COLUMN_CITY = "city";
    public static final String COLUMN_LAT = "lat";
    public static final String COLUMN_LON = "lon";
    public static final String COLUMN_COUNTRY = "country";
    public static final String COLUMN_PROVINCE = "province";
    public static final String COLUMN_PROVINCE_NOR = "province_nor";

    private Database database;
    @Inject
    public LocationDAOImpl(Database _Database) {
        this.database = _Database;
    }

    @Override
    public List<Location> getAllLocationData() {
        SQLiteDatabase database = this.database.open();

        List<Location> listLocation = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT * FROM " + TABLE_NAME,null);
        if(cursor.moveToFirst()){
            do{
                Location location = new Location();
                location.setCity(cursor.getString(cursor.getColumnIndex(COLUMN_PROVINCE)));
                location.setCountry(cursor.getString(cursor.getColumnIndex(COLUMN_COUNTRY)));
                location.setLat(cursor.getDouble(cursor.getColumnIndex(COLUMN_LAT)));
                location.setLon(cursor.getDouble(cursor.getColumnIndex(COLUMN_LON)));
                listLocation.add(location);

            }while (cursor.moveToNext());
        }
        cursor.close();
        this.database.close();
        return listLocation;
    }

    @Override
    public List<Location> searchLocation(String _location) {
        SQLiteDatabase database = this.database.open();

        List<Location> listLocation = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT DISTINCT " + COLUMN_PROVINCE + "," + COLUMN_COUNTRY + "," + COLUMN_LAT + "," + COLUMN_LON + " FROM " + TABLE_NAME + " WHERE " + COLUMN_PROVINCE_NOR + " LIKE " + "?" + " GROUP BY " + COLUMN_PROVINCE,
                new String[]{"%"+_location+"%"});
        if(cursor.moveToFirst()){
            do{
                Location location = new Location();
                location.setCity(cursor.getString(cursor.getColumnIndex(COLUMN_PROVINCE)));
                location.setCountry(cursor.getString(cursor.getColumnIndex(COLUMN_COUNTRY)));
                location.setLat(cursor.getDouble(cursor.getColumnIndex(COLUMN_LAT)));
                location.setLon(cursor.getDouble(cursor.getColumnIndex(COLUMN_LON)));
                listLocation.add(location);

            }while (cursor.moveToNext());
        }
        cursor.close();
        this.database.close();
        return listLocation;
    }
}

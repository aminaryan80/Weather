package com.app.weather.data;

import android.annotation.SuppressLint;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.text.SimpleDateFormat;
import java.util.Date;


@Entity(tableName = "city_data")
public class CityData {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "city")
    private String city;
    @ColumnInfo(name = "latitude")
    private double lat;
    @ColumnInfo(name = "longitude")
    private double lon;
    @ColumnInfo(name = "datetime")
    private String datetime;

    public CityData() {
    }

    public CityData(String city, double lat, double lon) {
        this.city = city;
        this.lat = lat;
        this.lon = lon;
        @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(System.currentTimeMillis());
        this.datetime = formatter.format(date);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }
}

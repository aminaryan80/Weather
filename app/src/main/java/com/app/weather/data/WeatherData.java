package com.app.weather.data;

import android.annotation.SuppressLint;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.text.SimpleDateFormat;
import java.util.Date;


@Entity(tableName = "weather_data")
public class WeatherData {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "lat")
    private double lat;
    @ColumnInfo(name = "lon")
    private double lon;
    @ColumnInfo(name = "weather_data")
    private String weather_data;
    @ColumnInfo(name = "datetime")
    private String datetime;

    public WeatherData() {
    }

    public WeatherData(double lat, double lon, String weather_data) {
        this.lat = lat;
        this.lon = lon;
        this.weather_data = weather_data;
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

    public String getWeather_data() {
        return weather_data;
    }

    public void setWeather_data(String weather_data) {
        this.weather_data = weather_data;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }
}

package com.app.weather.data;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface DataDao {
    @Query("SELECT * FROM city_data WHERE city=:city AND datetime >= datetime('now', '-12 hours') ORDER BY datetime(datetime) DESC LIMIT 1")
    List<CityData> getCoordinateByCity(String city);

    @Query("SELECT * FROM weather_data WHERE lat=:lat AND lon=:lon AND datetime >= datetime('now', '-12 hours') ORDER BY datetime(datetime) DESC LIMIT 1")
    List<WeatherData> getDataByCoordinate(double lat, double lon);

    @Insert
    void insertCityData(CityData... all_data);

    @Insert
    void insertWeatherData(WeatherData... all_data);

    @Query("DELETE FROM city_data where datetime < datetime('now', '-12 hours')")
    void deleteOldCityData();

    @Query("DELETE FROM weather_data where datetime < datetime('now', '-12 hours')")
    void deleteOldWeatherData();
}

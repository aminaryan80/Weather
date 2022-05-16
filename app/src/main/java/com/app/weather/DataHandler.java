package com.app.weather;

import android.content.Context;

import com.app.weather.data.AppDatabase;
import com.app.weather.data.CityData;
import com.app.weather.data.WeatherData;

import java.util.List;

public class DataHandler {
    private final AppDatabase db;

    public DataHandler(Context context) {
        this.db = AppDatabase.getDbInstance(context);
    }

    public void saveWeatherData(double latitude, double longitude, String response) {
        cleanup();
        WeatherData weatherData = new WeatherData(latitude, longitude, response);
        this.db.dataDao().insertWeatherData(weatherData);
    }

    public void saveCityData(String city, double latitude, double longitude) {
        cleanup();
        CityData cityData = new CityData(city, latitude, longitude);
        this.db.dataDao().insertCityData(cityData);
    }

    public double[] getCityData(String city) {
        cleanup();
        List<CityData> cityDataList = this.db.dataDao().getCoordinateByCity(city);
        if (cityDataList.size() == 0)
            return null;
        return new double[]{cityDataList.get(0).getLat(), cityDataList.get(0).getLon()};
    }

    public String getWeatherData(double lat, double lon) {
        cleanup();
        List<WeatherData> weatherDataList = this.db.dataDao().getDataByCoordinate(lat, lon);
        if (weatherDataList.size() == 0)
            return null;
        return weatherDataList.get(0).getWeather_data();
    }

    private void cleanup() {
        this.db.dataDao().deleteOldCityData();
        this.db.dataDao().deleteOldWeatherData();
    }
}

package com.app.weather;

import android.content.Context;
import android.os.AsyncTask;

import com.app.weather.data.AppDatabase;
import com.app.weather.data.CityData;
import com.app.weather.data.DataDao;
import com.app.weather.data.WeatherData;

import java.util.List;

public class DataRepository {
    private final DataDao dataDao;

    public DataRepository(Context context) {
        AppDatabase db = AppDatabase.getDbInstance(context);
        dataDao = db.dataDao();
    }

    private static class insertWeatherAsyncTask extends AsyncTask<WeatherData, Void, Void> {
        private final DataDao asyncTaskDao;

        insertWeatherAsyncTask(DataDao dao) {
            asyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(WeatherData... weatherData) {
            asyncTaskDao.insertWeatherData(weatherData);
            return null;
        }
    }

    private static class insertCityAsyncTask extends AsyncTask<CityData, Void, Void> {
        private final DataDao asyncTaskDao;

        insertCityAsyncTask(DataDao dao) {
            asyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(CityData... cityData) {
            asyncTaskDao.insertCityData(cityData);
            return null;
        }
    }

    public void saveWeatherData(double latitude, double longitude, String response) {
        cleanup();
        WeatherData weatherData = new WeatherData(latitude, longitude, response);
        new insertWeatherAsyncTask(dataDao).execute(weatherData);
    }

    public void saveCityData(String city, double latitude, double longitude) {
        cleanup();
        CityData cityData = new CityData(city, latitude, longitude);
        new insertCityAsyncTask(dataDao).execute(cityData);
    }

    public double[] getCityData(String city) {
        cleanup();
        List<CityData> cityDataList = dataDao.getCoordinateByCity(city);
        if (cityDataList.size() == 0)
            return null;
        return new double[]{cityDataList.get(0).getLat(), cityDataList.get(0).getLon()};
    }

    public String getWeatherData(double lat, double lon) {
        cleanup();
        List<WeatherData> weatherDataList = dataDao.getDataByCoordinate(lat, lon);
        if (weatherDataList.size() == 0)
            return null;
        return weatherDataList.get(0).getWeather_data();
    }

    private void cleanup() {
        dataDao.deleteOldCityData();
        dataDao.deleteOldWeatherData();
    }
}

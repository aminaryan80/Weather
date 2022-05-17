package com.app.weather;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.SavedStateHandle;

public class WeatherViewModel extends AndroidViewModel {
    private final DataRepository dataRepository;
    private final MutableLiveData<String> weatherLiveData;

    public WeatherViewModel (Application application, SavedStateHandle state) {
        super(application);
        dataRepository = new DataRepository(application);
        weatherLiveData = new MutableLiveData<>();
    }

    public MutableLiveData<String> getWeatherLiveData() {
        return weatherLiveData;
    }

    public void insertCityData(String city, double latitude, double longitude) {
        dataRepository.saveCityData(city, latitude, longitude);
    }

    public void insertWeatherData(double latitude, double longitude, String response) {
        dataRepository.saveWeatherData(latitude, longitude, response);
        setWeatherLiveData(response);
    }

    public double[] getCityDataFromDao(String city) {
        return dataRepository.getCityData(city);
    }

    public String getWeatherDataFromDao(double lat, double lon) {
        return dataRepository.getWeatherData(lat, lon);
    }

    public void setWeatherLiveData(String weatherData) {
        this.weatherLiveData.setValue(weatherData);
    }
}

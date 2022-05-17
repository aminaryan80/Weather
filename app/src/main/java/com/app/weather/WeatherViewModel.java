package com.app.weather;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.SavedStateHandle;

public class WeatherViewModel extends AndroidViewModel {
    private final DataRepository dataRepository;
    private final MutableLiveData<String> weatherData;
    private final MutableLiveData<double[]> cityData;

    public WeatherViewModel (Application application, SavedStateHandle state) {
        super(application);
        dataRepository = new DataRepository(application);
        weatherData = new MutableLiveData<>();
        cityData = new MutableLiveData<>();
    }

    public String getWeatherData() {
        return weatherData.getValue();
    }

    public void insertCityData(String city, double latitude, double longitude) {
        dataRepository.saveCityData(city, latitude, longitude);
        setCityData(new double[]{latitude, longitude});
    }

    public void insertWeatherData(double latitude, double longitude, String response) {
        dataRepository.saveWeatherData(latitude, longitude, response);
        setWeatherData(response);
    }

    public MutableLiveData<double[]> getCityDataFromDao(String city) {
        double[] data = dataRepository.getCityData(city);
        setCityData(data);
        return cityData;
    }

    public MutableLiveData<String> getWeatherDataFromDao(double lat, double lon) {
        String data = dataRepository.getWeatherData(lat, lon);
        setWeatherData(data);
        return weatherData;
    }

    public void setCityData(double[] coordination) {
        this.cityData.setValue(coordination);
    }

    public void setWeatherData(String weatherData) {
        this.weatherData.setValue(weatherData);
    }
}

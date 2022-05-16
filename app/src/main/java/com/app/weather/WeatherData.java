package com.app.weather;

public class WeatherData {
    String day;
    String month;
    String temp;
    String feelsLike;
    String humidity;
    String windSpeed;
    int weatherImage;

    public WeatherData(
            String day,
            String month,
            String temp,
            String feelsLike,
            String humidity,
            String windSpeed,
            int weatherImage
    ) {
        this.day = day;
        this.month = month;
        this.temp = temp;
        this.feelsLike = feelsLike;
        this.humidity = humidity;
        this.windSpeed = windSpeed;
        this.weatherImage = weatherImage;
    }
}
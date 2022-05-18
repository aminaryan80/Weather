package com.app.weather;

import android.annotation.SuppressLint;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class WeatherUtils {

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    public static String[] getDate(int i) {
        String[] today = new String[2];

        Calendar cal = Calendar.getInstance();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat month_date = new SimpleDateFormat("MMM");
        @SuppressLint("SimpleDateFormat") SimpleDateFormat day_date = new SimpleDateFormat("d");

        cal.add(Calendar.DAY_OF_YEAR, i);
        today[0] = day_date.format(cal.getTime());
        today[1] = month_date.format(cal.getTime());

        return today;
    }

    public static int getIconWeather(String weatherType) {
        switch (weatherType) {
            case "Clear":
                return R.drawable.clear;
            case "Clouds":
                return R.drawable.clouds;
            case "Snow":
                return R.drawable.snow;
            case "Rain":
                return R.drawable.rain;
            case "Drizzle":
                return R.drawable.drizzle;
            case "Thunderstorm":
                return R.drawable.thunderstorm;
            default:
                return R.drawable.foggy;
        }
    }

}

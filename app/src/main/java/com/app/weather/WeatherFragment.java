package com.app.weather;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class WeatherFragment extends Fragment {
    private EditText cityNameEditText;
    private EditText latitudeEditText;
    private EditText longitudeEditText;
    private final Handler handler = new Handler();
    private View view;
    private Context context;
    private final static String TOKEN = "b896d5e04fe88709659757a67e6d57bb";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_weather, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.view = view;
        this.context = view.getContext();

        LinearLayout cityNameLayout = view.findViewById(R.id.cityNameLayout);
        LinearLayout locationLayout = view.findViewById(R.id.locationLayout);

        RadioGroup radioGroup = (RadioGroup) view.findViewById(R.id.groupradio);
        radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            handler.removeCallbacksAndMessages(null);
            RadioButton radioButton = (RadioButton) group.findViewById(checkedId);
            if (radioButton.getText().equals("Location")) {
                cityNameLayout.setVisibility(View.GONE);
                locationLayout.setVisibility(View.VISIBLE);
            } else {
                locationLayout.setVisibility(View.GONE);
                cityNameLayout.setVisibility(View.VISIBLE);
            }
        });

        cityNameEditText = (EditText) view.findViewById(R.id.cityNameEditText);
        latitudeEditText = (EditText) view.findViewById(R.id.latitudeEditText);
        longitudeEditText = (EditText) view.findViewById(R.id.longitudeEditText);

        cityNameEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                handler.removeCallbacksAndMessages(null);
            }

            @Override
            public void afterTextChanged(Editable editable) {
                String city = editable.toString();
                Runnable runnable = () -> getItemsByCity(city);
                runDelayed(runnable, 5000);
            }
        });

        latitudeEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                handler.removeCallbacksAndMessages(null);
            }

            @Override
            public void afterTextChanged(Editable editable) {
                try {
                    double latitude = Double.parseDouble(editable.toString());
                    double longitude = Double.parseDouble(longitudeEditText.getText().toString());
                    Runnable runnable = () -> getItems(latitude, longitude);
                    runDelayed(runnable, 5000);
                } catch (NumberFormatException ignored) {
                }
            }
        });

        longitudeEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                handler.removeCallbacksAndMessages(null);
            }

            @Override
            public void afterTextChanged(Editable editable) {
                try {
                    double longitude = Double.parseDouble(editable.toString());
                    double latitude = Double.parseDouble(latitudeEditText.getText().toString());
                    Runnable runnable = () -> getItems(latitude, longitude);
                    runDelayed(runnable, 5000);
                } catch (NumberFormatException ignored) {
                }
            }
        });

        cityNameEditText.setOnKeyListener((View.OnKeyListener) (view1, i, keyEvent) -> {
            if (i == KeyEvent.KEYCODE_ENTER) {
                handler.removeCallbacksAndMessages(null);
                String city = cityNameEditText.getText().toString();
                Runnable runnable = () -> getItemsByCity(city);
                runDelayed(runnable, 0);
                closeKeyboard();
                return true;
            }
            return false;
        });

        latitudeEditText.setOnKeyListener((View.OnKeyListener) (view1, i, keyEvent) -> {
            if (i == KeyEvent.KEYCODE_ENTER) {
                handler.removeCallbacksAndMessages(null);
                try {
                    double latitude = Double.parseDouble(latitudeEditText.getText().toString());
                    double longitude = Double.parseDouble(longitudeEditText.getText().toString());
                    Runnable runnable = () -> getItems(latitude, longitude);
                    runDelayed(runnable, 0);
                } catch (NumberFormatException ignored) {
                }
                closeKeyboard();
                return true;
            }
            return false;
        });

        longitudeEditText.setOnKeyListener((View.OnKeyListener) (view1, i, keyEvent) -> {
            if (i == KeyEvent.KEYCODE_ENTER) {
                handler.removeCallbacksAndMessages(null);
                try {
                    double latitude = Double.parseDouble(latitudeEditText.getText().toString());
                    double longitude = Double.parseDouble(longitudeEditText.getText().toString());
                    Runnable runnable = () -> getItems(latitude, longitude);
                    runDelayed(runnable, 0);
                } catch (NumberFormatException ignored) {
                }
                closeKeyboard();
                return true;
            }
            return false;
        });
    }

    private void closeKeyboard() {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    private void runDelayed(Runnable runnable, int delay) {
        handler.postDelayed(runnable, delay);
    }

    private void getItemsByCity(String city) {
        String url = "https://api.openweathermap.org/geo/1.0/direct?q=<CITY>&limit=1&appid=<TOKEN>";
        url = url.replace("<CITY>", city);
        url = url.replace("<TOKEN>", TOKEN);
        StringRequest myRequest = new StringRequest(Request.Method.GET, url,
                response -> {
                    try {
                        Toast.makeText(context, "Start2", Toast.LENGTH_LONG).show();
                        JSONObject responseJsonObject = (JSONObject) new JSONArray(response).get(0);
                        int latitude = responseJsonObject.getInt("lat");
                        int longitude = responseJsonObject.getInt("lon");
                        getItems(latitude, longitude);
                    } catch (JSONException e) {
                        Toast.makeText(context, "Invalid city name", Toast.LENGTH_LONG).show();
                        e.printStackTrace();
                    }
                },
                volleyError -> {
                    String errorMessage = volleyError.getMessage();
                    if (errorMessage == null) {
                        Toast.makeText(context, "ERROR WHILE GETTING COORDS!", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show();
                    }
                }
        );
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(myRequest);
    }

    private void getItems(double latitude, double longitude) {
        String url = "https://api.openweathermap.org/data/2.5/onecall?lat=<LAT>&lon=<LON>&exclude=hourly,minutely,alerts&appid=<TOKEN>&units=metric";
        url = url.replace("<LAT>", String.valueOf(latitude));
        url = url.replace("<LON>", String.valueOf(longitude));
        url = url.replace("<TOKEN>", TOKEN);
        StringRequest myRequest = new StringRequest(Request.Method.GET, url,
                response -> {
                    try {
                        Toast.makeText(context, "Start", Toast.LENGTH_LONG).show();
                        JSONObject responseJsonObject = new JSONObject(response);
                        JSONObject currentJsonObject = responseJsonObject.getJSONObject("current");
                        String temp = currentJsonObject.getString("temp");
                        String feelsLike = currentJsonObject.getString("feels_like");
                        String windSpeed = currentJsonObject.getString("wind_speed");
                        String humidity = currentJsonObject.getString("humidity");
                        String weatherType = ((JSONObject) currentJsonObject.getJSONArray("weather").get(0)).getString("main");
                        int iconWeather = getIconWeather(weatherType);
                        populateTodayData(temp, feelsLike, humidity, windSpeed, iconWeather);
                        JSONArray dailyJsonArray = responseJsonObject.getJSONArray("daily");
                        String[][] dailyData = new String[dailyJsonArray.length()][5];
                        for (int i = 1; i < dailyJsonArray.length(); i++) {
                            JSONObject dayJsonObject = (JSONObject) dailyJsonArray.get(i);
                            dailyData[i][0] = dayJsonObject.getJSONObject("temp").getString("max");
                            dailyData[i][1] = dayJsonObject.getJSONObject("temp").getString("min");
                            dailyData[i][2] = dayJsonObject.getString("humidity");
                            dailyData[i][3] = dayJsonObject.getString("wind_speed");
                            String dayWeatherType = ((JSONObject) dayJsonObject.getJSONArray("weather").get(0)).getString("main");
                            dailyData[i][4] = String.valueOf(getIconWeather(dayWeatherType));
                        }
                        // populateDailyData(dailyData); // TODO: RecyclerView
                    } catch (JSONException e) {
                        Toast.makeText(context, "ERROR!", Toast.LENGTH_LONG).show();
                        e.printStackTrace();
                    }
                },
                volleyError -> {
                    String errorMessage = volleyError.getMessage();
                    if (errorMessage == null) {
                        Toast.makeText(context, "Invalid latitude and longitude", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show();
                    }
                }
        );
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(myRequest);
    }

    @SuppressLint("SetTextI18n")
    private void populateTodayData(String temp, String feelsLike, String humidity, String windSpeed, int iconWeather) {
        TextView dayDateTextView = view.findViewById(R.id.dayDateTextView);
        TextView monthDateTextView = view.findViewById(R.id.monthDateTextView);
        TextView tempTextView = view.findViewById(R.id.tempTextView);
        TextView feelsLikeTextView = view.findViewById(R.id.feelsLikeTextView);
        TextView humidityTextView = view.findViewById(R.id.humidityTextView);
        TextView windTextView = view.findViewById(R.id.windTextView);
        ImageView weatherImageView = view.findViewById(R.id.weatherImageView);

        Calendar cal = Calendar.getInstance();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat month_date = new SimpleDateFormat("MMM");
        @SuppressLint("SimpleDateFormat") SimpleDateFormat day_date = new SimpleDateFormat("d");

        dayDateTextView.setText(day_date.format(cal.getTime()));
        monthDateTextView.setText(month_date.format(cal.getTime()));
        tempTextView.setText(temp + "°");
        feelsLikeTextView.setText("feels like " + feelsLike + "°");
        humidityTextView.setText(humidity + "%");
        windTextView.setText(windSpeed);
        weatherImageView.setImageResource(iconWeather);
    }

    private int getIconWeather(String weatherType) {
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
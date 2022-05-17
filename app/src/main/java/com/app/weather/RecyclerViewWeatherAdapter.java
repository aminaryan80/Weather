package com.app.weather;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;


public class RecyclerViewWeatherAdapter extends RecyclerView.Adapter<RecyclerViewWeatherAdapter.WeatherViewHolder> {
    protected final Context context;
    protected String[][] weatherData;

    public RecyclerViewWeatherAdapter(String[][] weatherData, Context context) {
        this.weatherData = weatherData;
        this.context = context;
    }

    public static class WeatherViewHolder extends RecyclerView.ViewHolder {
        ConstraintLayout weatherLayout;
        TextView dayDateTextView;
        TextView monthDateTextView;
        TextView tempTextView;
        TextView humidityTextView;
        TextView windTextView;
        ImageView weatherImageView;

        public WeatherViewHolder(View itemView) {
            super(itemView);
            weatherLayout = itemView.findViewById(R.id.weatherLayout);
            dayDateTextView = itemView.findViewById(R.id.dayDateTextView);
            monthDateTextView = itemView.findViewById(R.id.monthDateTextView);
            tempTextView = itemView.findViewById(R.id.tempTextView);
            humidityTextView = itemView.findViewById(R.id.humidityTextView);
            windTextView = itemView.findViewById(R.id.windTextView);
            weatherImageView = itemView.findViewById(R.id.weatherImageView);
        }
    }

    @NonNull
    @Override
    public WeatherViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View courseView = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.daily_weather_item, parent, false
        );
        return new WeatherViewHolder(courseView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull WeatherViewHolder holder, int position) {
        String[] data = weatherData[position];
        holder.dayDateTextView.setText(data[0]);
        holder.monthDateTextView.setText(data[1]);
        holder.tempTextView.setText(data[2] + "/" + data[3]);
        holder.humidityTextView.setText(data[4] + "%");
        holder.windTextView.setText(data[5]);
        holder.weatherImageView.setImageResource(Integer.parseInt(data[6]));
    }

    @Override
    public int getItemCount() {
        return weatherData.length;
    }

    public void clear() {
        int size = weatherData.length;
        if (size > 0) {
            weatherData = new String[0][0];
            notifyItemRangeRemoved(0, size);
        }
    }
}
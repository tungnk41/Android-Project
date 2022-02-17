package com.example.openweather.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.openweather.Model.Forecast.Hourly;
import com.example.openweather.R;
import com.example.openweather.Utils.WeatherUtils;

import java.util.List;

public class WeatherHourlyAdapter extends RecyclerView.Adapter<WeatherHourlyAdapter.HourlyViewHolder>{
    private List<Hourly> listWeatherHourly;
    private Context context;
    private WeatherUtils weatherUtils;

    public WeatherHourlyAdapter(Context context, List<Hourly> listWeatherHourly, WeatherUtils weatherUtils) {
        this.listWeatherHourly = listWeatherHourly;
        this.context = context;
        this.weatherUtils = weatherUtils;
    }

    @NonNull
    @Override
    public HourlyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_weather_hourly,parent,false);
        return new HourlyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HourlyViewHolder holder, int position) {
        Hourly hourly = listWeatherHourly.get(position);
        if(holder == null){
            return;
        }

        String time = weatherUtils.timeFormatter(hourly.getTime());
        String temp = weatherUtils.temperatureUtil((int)hourly.getTemperature());
        String imgWeather = hourly.getListWeather().get(0).getIcon();

        String url = "https://openweathermap.org/img/wn/" + imgWeather + "@2x.png";
        Glide.with(context)
                .load(url)
                .into(holder.imgWeather);

        holder.tvTime.setText(time);
        holder.tvTemperature.setText(temp);

    }

    @Override
    public int getItemCount() {
        if(listWeatherHourly != null){
            return listWeatherHourly.size();
        }
        return 0;
    }

    public void setData(List<Hourly> listWeatherHourly){
        this.listWeatherHourly = listWeatherHourly;
        notifyDataSetChanged();
    }


    public class HourlyViewHolder extends RecyclerView.ViewHolder{

        private TextView tvTime;
        private ImageView imgWeather;
        private TextView tvTemperature;

        public HourlyViewHolder(@NonNull View itemView) {
            super(itemView);

            tvTime = itemView.findViewById(R.id.tvTime);
            tvTemperature = itemView.findViewById(R.id.tvTemperature);
            imgWeather = itemView.findViewById(R.id.imgWeather);
        }
    }
}

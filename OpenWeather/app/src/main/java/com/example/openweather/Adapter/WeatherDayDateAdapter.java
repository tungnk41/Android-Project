package com.example.openweather.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.openweather.Model.Forecast.Hourly;
import com.example.openweather.Model.WeatherDayDate;
import com.example.openweather.R;
import com.example.openweather.Utils.WeatherUtils;

import java.util.List;

public class WeatherDayDateAdapter extends RecyclerView.Adapter<WeatherDayDateAdapter.WeatherDayDateViewHolder>{
    public interface ItemClickListener{
        void onItemClicked(int index);
    }
    private List<WeatherDayDate> listWeatherDayDate;
    private int selectedItem = 0;
    WeatherDayDateAdapter.ItemClickListener itemClickListener;

    public WeatherDayDateAdapter(List<WeatherDayDate> listWeatherDayDate,ItemClickListener itemClickListener) {
        this.listWeatherDayDate = listWeatherDayDate;
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public WeatherDayDateViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_weather_day_date,parent,false);
        return new WeatherDayDateViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WeatherDayDateViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        if(listWeatherDayDate != null){
            return listWeatherDayDate.size();
        }
        return 0;
    }

    public void setData(List<WeatherDayDate> listWeatherDayDate){
        this.listWeatherDayDate = listWeatherDayDate;
        notifyDataSetChanged();
    }

    public void setSelectedItem(int position){
        this.selectedItem = position;
        notifyDataSetChanged();
    }


    public class WeatherDayDateViewHolder extends RecyclerView.ViewHolder{

        private TextView tvDay;
        private TextView tvDate;

        public WeatherDayDateViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDay = itemView.findViewById(R.id.tvDay);
            tvDate = itemView.findViewById(R.id.tvDate);
        }

        public void bind(int position){
            WeatherDayDate weatherDayDate = listWeatherDayDate.get(position);
            tvDay.setText(weatherDayDate.getDay().substring(0,3));
            tvDate.setText(weatherDayDate.getDate());

            if(position == selectedItem){
                itemView.setBackgroundColor(Color.parseColor("#e7e7e7"));
            }else{
                itemView.setBackgroundColor(0x00000000); // Transparent color
            }

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    itemClickListener.onItemClicked(position);
                }
            });
        }

    }
}
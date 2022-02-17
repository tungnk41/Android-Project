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
import com.example.openweather.Model.Forecast.Daily;
import com.example.openweather.Model.Forecast.Weather;
import com.example.openweather.R;
import com.example.openweather.Utils.WeatherUtils;

import java.util.List;

public class WeatherDailyAdapter extends RecyclerView.Adapter<WeatherDailyAdapter.DailyViewHolder>{

    public interface ItemClickListener{
        void onItemClicked(int index);
    }
    private List<Daily> listDailyWeather;
    private Context context;
    private WeatherUtils weatherUtils;
    ItemClickListener itemClickListener;

    public WeatherDailyAdapter(Context context, List<Daily> listDailyWeather, WeatherUtils weatherUtils, ItemClickListener itemClickListener) {
        this.listDailyWeather = listDailyWeather;
        this.context = context;
        this.weatherUtils = weatherUtils;
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public DailyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_weather_daily,parent,false);
        return new DailyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DailyViewHolder holder, int position) {
        holder.bind(position,itemClickListener);

    }

    @Override
    public int getItemCount() {
        if(listDailyWeather != null){
            return listDailyWeather.size();
        }
        return 0;
    }

    public void setData(List<Daily> listDailyWeather){
        this.listDailyWeather = listDailyWeather;
        notifyDataSetChanged();
    }

    public class DailyViewHolder extends RecyclerView.ViewHolder{

        TextView tvDay;
        TextView tvTempMax;
        TextView tvTempMin;
        ImageView imgWeather;
        public DailyViewHolder(@NonNull View itemView) {
            super(itemView);

            tvDay = itemView.findViewById(R.id.tvDay);
            tvTempMax = itemView.findViewById(R.id.tvTempMax);
            tvTempMin = itemView.findViewById(R.id.tvTempMin);
            imgWeather = itemView.findViewById(R.id.imgWeather);
        }

        public void bind(int position, ItemClickListener itemClickListener){
            Daily daily = listDailyWeather.get(position);

            String _day = weatherUtils.dayFormatter(daily.getDt());
            String _tempMax = weatherUtils.temperatureUtil((int)daily.getTemperatureDaily().getTempMax());
            String _tempMin = weatherUtils.temperatureUtil((int)daily.getTemperatureDaily().getTempMin());
            String _imgWeather = daily.getListWeather().get(0).getIcon();

            String url = "https://openweathermap.org/img/wn/" + _imgWeather + "@2x.png";
            Glide.with(context)
                    .load(url)
                    .into(imgWeather);

            tvTempMax.setText(_tempMax);
            tvTempMin.setText(_tempMin);
            if(position == 0){
                tvDay.setText("Today");
            }else{
                tvDay.setText(_day);
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

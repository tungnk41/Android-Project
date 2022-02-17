package com.example.openweather.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.openweather.Model.WeatherDailyDetail;
import com.example.openweather.R;
import com.example.openweather.Utils.WeatherUtils;

import java.util.List;


public class WeatherDailyDetaiListInforAdapter extends RecyclerView.Adapter<WeatherDailyDetaiListInforAdapter.DailyDetailViewHolder>{

    private List<WeatherDailyDetail> listWeatherDailyDetail;

    public WeatherDailyDetaiListInforAdapter(List<WeatherDailyDetail> listWeatherDailyDetail) {
        this.listWeatherDailyDetail = listWeatherDailyDetail;
    }

    @NonNull
    @Override
    public DailyDetailViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_frag_weather_daily_detail,parent,false);
        return new DailyDetailViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DailyDetailViewHolder holder, int position) {
        holder.bind(position);

    }

    @Override
    public int getItemCount() {
        if(listWeatherDailyDetail != null){
            return listWeatherDailyDetail.size();
        }
        return 0;
    }

    public void setData(List<WeatherDailyDetail> listWeatherDailyDetail){
        this.listWeatherDailyDetail = listWeatherDailyDetail;
        notifyDataSetChanged();
    }

    public class DailyDetailViewHolder extends RecyclerView.ViewHolder{

        TextView tvItemName;
        TextView tvItemValue;

        public DailyDetailViewHolder(@NonNull View itemView) {
            super(itemView);

            tvItemName = itemView.findViewById(R.id.tvItemName);
            tvItemValue = itemView.findViewById(R.id.tvItemValue);
        }

        public void bind(int position){
            WeatherDailyDetail weatherDailyDetail = listWeatherDailyDetail.get(position);

            String _tvItemName = weatherDailyDetail.getItemName();
            String _tvItemValue = weatherDailyDetail.getItemValue();

            tvItemName.setText(_tvItemName);
            tvItemValue.setText(_tvItemValue);
        }
    }
}
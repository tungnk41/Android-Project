package com.example.openweather.Adapter;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.openweather.Model.Location.Location;
import com.example.openweather.R;

import java.util.List;

import timber.log.Timber;

public class SearchLocationAdapter extends RecyclerView.Adapter<SearchLocationAdapter.SearchLocationViewHolder> {

    public interface ItemAdapterClicked {
        void onItemClicked(Location searchLocation);
    }


    private List<Location> listSearchLocation;
    private ItemAdapterClicked itemAdapterClicked;

    public SearchLocationAdapter(List<Location> listSearchLocation, ItemAdapterClicked itemAdapterClicked) {
        this.listSearchLocation = listSearchLocation;
        this.itemAdapterClicked = itemAdapterClicked;
    }

    @NonNull
    @Override
    public SearchLocationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_search_location,parent,false);
        return new SearchLocationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchLocationViewHolder holder, int position) {
        Location searchLocation = listSearchLocation.get(position);
        if(searchLocation == null){
            return;
        }
        holder.tvSearchLocation.setText(searchLocation.getCity() + ", " + searchLocation.getCountry());

        holder.itemSearchLocationLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemAdapterClicked.onItemClicked(searchLocation);

            }
        });
    }

    @Override
    public int getItemCount() {
        if(listSearchLocation != null){
            return listSearchLocation.size();
        }
        return 0;
    }


    public class SearchLocationViewHolder extends RecyclerView.ViewHolder{
        private TextView tvSearchLocation;
        private LinearLayout itemSearchLocationLayout;

        public SearchLocationViewHolder(@NonNull View itemView) {
            super(itemView);
            tvSearchLocation = itemView.findViewById(R.id.tvLocationName);
            itemSearchLocationLayout = itemView.findViewById(R.id.itemSearchLocationLayout);

        }
    }
}

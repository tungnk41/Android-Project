package com.example.openweather.View.SubActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.SearchView;

import com.example.openweather.Adapter.SearchLocationAdapter;
import com.example.openweather.DataLocal.SharePreferencesManager;
import com.example.openweather.Model.Location.Location;
import com.example.openweather.R;
import com.example.openweather.View.MainActivity;
import com.example.openweather.ViewModel.SearchLocation.SearchLocationViewModel;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class SearchLocationActivity extends AppCompatActivity {
    private SearchView svSearchLocation;
    private RecyclerView rvSearchLocation;
    private SearchLocationViewModel searchLocationViewModel;
    private SearchLocationAdapter searchLocationAdapter;
    private LinearLayoutManager linearLayoutManager;

    @Inject
    SharePreferencesManager sharePreferencesManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_location);
        svSearchLocation = findViewById(R.id.svSearchLocation);
        rvSearchLocation = findViewById(R.id.rvSearchLocation);
        initViewModel();
        initRecycleView();
        svSearchLocation.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){

                }
            }
        });

        svSearchLocation.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                svSearchLocation.clearFocus();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                searchLocationViewModel.requestListSearchLocation(query);
                return false;
            }
        });

    }

    private void initViewModel(){
        linearLayoutManager = new LinearLayoutManager(this);
        rvSearchLocation.setLayoutManager(linearLayoutManager);

        searchLocationViewModel = new ViewModelProvider(this).get(SearchLocationViewModel.class);
        searchLocationViewModel.getListSearchLocationLiveData().observe(this, new Observer<List<Location>>() {
            @Override
            public void onChanged(List<Location> listSearchLocations) {
                searchLocationAdapter = new SearchLocationAdapter( listSearchLocations, new SearchLocationAdapter.ItemAdapterClicked() {
                    @Override
                    public void onItemClicked(Location searchLocation) {
                        String location = searchLocation.getCity() + ", " + searchLocation.getCountry();
                        String lat = String.valueOf(searchLocation.getLat());
                        String lon = String.valueOf(searchLocation.getLon());

                        Intent intent = new Intent();
                        intent.putExtra("location",location);
                        intent.putExtra("lat",lat);
                        intent.putExtra("lon",lon);
                        setResult(RESULT_OK, intent);
                        finish();
                    }
                });
                rvSearchLocation.setAdapter(searchLocationAdapter);
            }
        });
    }

    private void initRecycleView(){
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(rvSearchLocation.getContext(),linearLayoutManager.getOrientation());
        rvSearchLocation.addItemDecoration(dividerItemDecoration);
        rvSearchLocation.setHasFixedSize(true);
    }

}
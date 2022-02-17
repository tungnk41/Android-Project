package com.example.openweather.ViewModel.SearchLocation;

import static android.content.ContentValues.TAG;

import android.app.Activity;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.openweather.Hilt.Qualifier.LocalRepository;
import com.example.openweather.Model.Location.Location;
import com.example.openweather.Repository.Repository;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class SearchLocationViewModel extends ViewModel {

    private MutableLiveData<List<Location>> listSearchLocationLiveData;
    private Repository repository;

    @Inject
    public SearchLocationViewModel(@LocalRepository Repository repository) {
        this.repository = repository;
        listSearchLocationLiveData = new MutableLiveData<>();
        initData();
    }

    private void initData(){
        listSearchLocationLiveData.setValue(new ArrayList<>());
    }

    public MutableLiveData<List<Location>> getListSearchLocationLiveData(){
        return listSearchLocationLiveData;
    }

    public void requestListSearchLocation(String location){

        repository.searchLocation(location.toLowerCase(), new Repository.ResultSearchLocation() {
            @Override
            public void onSuccess(List<Location> listSearchLocation) {
                listSearchLocationLiveData.postValue(listSearchLocation);
            }

            @Override
            public void onFailure(Throwable t) {
                t.printStackTrace();
            }
        });
    }

}

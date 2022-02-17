package com.example.openweather.Utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;

import javax.inject.Inject;

import dagger.hilt.android.qualifiers.ApplicationContext;

public class NetworkHelper {
    private Context context;

    @Inject
    public NetworkHelper(@ApplicationContext Context context){
        this.context = context;
    }

    public boolean isNetworkAvailable(){
        if(context == null){
            return false;
        }

        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager == null){
            return false;
        }

        Network network = connectivityManager.getActiveNetwork();
        if(network == null){
            return false;
        }

        NetworkCapabilities capabilities = connectivityManager.getNetworkCapabilities(network);
        return capabilities != null &&
                ((capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI))
                        ||(capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)));
    }
}

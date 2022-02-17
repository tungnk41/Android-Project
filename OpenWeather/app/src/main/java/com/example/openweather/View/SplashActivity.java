package com.example.openweather.View;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import com.example.openweather.AppWeather;
import com.example.openweather.R;
import com.example.openweather.Utils.NetworkHelper;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class SplashActivity extends AppCompatActivity {

    @Inject
    NetworkHelper networkHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        //Must check permission before running app
        requestPermission();

    }


    //3rd Library : TedPermission
    private void requestPermission(){
        String[] permission = {
                Manifest.permission.INTERNET,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_NETWORK_STATE,
        };

        TedPermission.with(this)
                .setPermissionListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted() {
                        if(networkHelper.isNetworkAvailable()){
                            requestMainActivityScreen();
                        }else{
                            requestNoConnectionScreen();
                        }
                    }

                    @Override
                    public void onPermissionDenied(List<String> deniedPermissions) {
                        requestNoConnectionScreen();
                    }
                })
                .setPermissions(permission[0],permission[1],permission[2],permission[3])
                .setDeniedTitle("Open Setting")
                .setDeniedMessage("Go to Setting -> Permission")
                .check();
    }


    private void requestMainActivityScreen(){
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        },2000);

    }

    private void requestNoConnectionScreen(){
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this,NoConnectionActivity.class);
                startActivity(intent);
                finish();
                overridePendingTransition(0, 0);
            }
        },2000);
    }
}
package com.example.openweather.View;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;

import com.example.openweather.R;

public class NoConnectionActivity extends AppCompatActivity {
    Button btnTryAgain;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_no_connection);
        btnTryAgain =  findViewById(R.id.btnTryAgain);

        btnTryAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBtnTryAgainClicked();
            }
        });
    }

    private void onBtnTryAgainClicked(){
        Intent intent = new Intent(NoConnectionActivity.this,SplashActivity.class);
        startActivity(intent);
        finish();
    }
}
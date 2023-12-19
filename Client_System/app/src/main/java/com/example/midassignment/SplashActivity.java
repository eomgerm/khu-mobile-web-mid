package com.example.midassignment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import utils.SharedPreferencesManager;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        if (SharedPreferencesManager.getAccessToken(this).isEmpty()) {
            Log.d("Splash", "온보딩 화면으로 이동");
            goToOnBoarding();
        } else {
            Log.d("Splash", "홈 화면으로 이동");
            goToHome();
        }
    }

    private void goToHome() {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
        finish();
    }

    private void goToOnBoarding() {
        Intent intent = new Intent(this, OnBoardingActivity.class);
        startActivity(intent);
        finish();
    }
}
package com.example.mealplanner.SplashActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.LottieAnimationView;
import com.example.mealplanner.Authentication.Login.LoginView.LoginActivity;
import com.example.mealplanner.R;

public class SplashActivity extends AppCompatActivity {

    private static final int SPLASH_SCREEN_TIME = 6000; // 3 seconds

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        LottieAnimationView lottieAnimationView = findViewById(R.id.lottieAnimationView);
        lottieAnimationView.playAnimation();

        new Handler().postDelayed(() -> {
            Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }, SPLASH_SCREEN_TIME);
    }

}
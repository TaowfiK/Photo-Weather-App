package com.example.photoweather;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.photoweather.databinding.FragmentHistoryListBinding;

public class InitialActivity extends AppCompatActivity {

    // Views
    private ImageView splashScreenImageView;
    private TextView photoWeatherTextView;
    private TextView vibeTextView;
    private Button startButton;

    //animation
    Animation topAnim, bottomAnim, vibeFadeInAnim, buttonFadeInAnim;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        //fullscreen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //animation
        topAnim = AnimationUtils.loadAnimation(this, R.anim.top_animation);
        bottomAnim = AnimationUtils.loadAnimation(this, R.anim.bottom_animation);
        vibeFadeInAnim = AnimationUtils.loadAnimation(this, R.anim.fade_in_animation);
        buttonFadeInAnim = AnimationUtils.loadAnimation(this, R.anim.fade_in_animation);
        topAnim.setDuration(1500);
        bottomAnim.setDuration(1500);
        vibeFadeInAnim.setDuration(4000);
        buttonFadeInAnim.setDuration(1000);


        //views
        splashScreenImageView = findViewById(R.id.splash_image_view);
        photoWeatherTextView = findViewById(R.id.photo_weather_text_view);
        vibeTextView = findViewById(R.id.vibe_text_view);
        startButton = findViewById(R.id.startButton);

        photoWeatherTextView.setAnimation(topAnim);
        splashScreenImageView.setAnimation(bottomAnim);
        vibeTextView.setAnimation(vibeFadeInAnim);
        startButton.setAnimation(buttonFadeInAnim);

        setCounterForStartButton();

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(InitialActivity.this, MainActivity.class);
                InitialActivity.this.startActivity(myIntent);
            }
        });
    }

    private void setCounterForStartButton(){
        new CountDownTimer(2500, 1000) {

            public void onTick(long millisUntilFinished) {
            }

            public void onFinish() {
                startButton.setVisibility(View.VISIBLE);
            }

        }.start();
    }
}

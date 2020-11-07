package com.example.photoweather.views;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.photoweather.R;

public class SplashScreenFragment extends Fragment {

    // Constants
    private final static String TAG = SplashScreenFragment.class.getSimpleName();

    // Views
    private ImageView splashScreenImageView;
    private TextView photoWeatherTextView;
    private TextView vibeTextView;
    private Button startButton;

    //animation
    Animation topAnim, bottomAnim, vibeFadeInAnim, buttonFadeInAnim;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_splash_screen, container, false);

        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //animation
        topAnim = AnimationUtils.loadAnimation(getContext(), R.anim.top_animation);
        bottomAnim = AnimationUtils.loadAnimation(getContext(), R.anim.bottom_animation);
        vibeFadeInAnim = AnimationUtils.loadAnimation(getContext(), R.anim.fade_in_animation);
        buttonFadeInAnim = AnimationUtils.loadAnimation(getContext(), R.anim.fade_in_animation);
        topAnim.setDuration(1500);
        bottomAnim.setDuration(1500);
        vibeFadeInAnim.setDuration(4000);
        buttonFadeInAnim.setDuration(1000);


        //views
        splashScreenImageView = view.findViewById(R.id.splash_image_view);
        photoWeatherTextView = view.findViewById(R.id.photo_weather_text_view);
        vibeTextView = view.findViewById(R.id.vibe_text_view);
        startButton = view.findViewById(R.id.startButton);

        photoWeatherTextView.setAnimation(topAnim);
        splashScreenImageView.setAnimation(bottomAnim);
        vibeTextView.setAnimation(vibeFadeInAnim);
        startButton.setAnimation(buttonFadeInAnim);

        new CountDownTimer(3000, 1000) {

            public void onTick(long millisUntilFinished) {
            }

            public void onFinish() {
                startButton.setVisibility(View.VISIBLE);
            }

        }.start();

    }
}

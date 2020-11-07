package com.example.photoweather.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
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
                R.layout.activity_splash_screen, container, false);

        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);



    }
}

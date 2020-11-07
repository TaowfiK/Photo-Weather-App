package com.example.photoweather;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.WindowManager;

import com.example.photoweather.utils.GuiManager;
import com.example.photoweather.views.SplashScreenFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //fullscreen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        GuiManager.getInstance().setFragmentManager(getSupportFragmentManager());
        GuiManager.getInstance().setFragmentFrame(R.id.fragment_container);
        GuiManager.getInstance().setCurrentFragment(new SplashScreenFragment(), 0, 0);
    }
}
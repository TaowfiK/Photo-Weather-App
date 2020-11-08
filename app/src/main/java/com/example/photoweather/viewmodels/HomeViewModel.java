package com.example.photoweather.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.photoweather.models.weather.CurrentWeatherResponse;
import com.example.photoweather.networking.NetworkState;
import com.example.photoweather.repositories.MainRepository;

public class HomeViewModel extends ViewModel {

    private MainRepository repository;

    public HomeViewModel()
    {
        repository = new MainRepository();
    }

    public LiveData<CurrentWeatherResponse> getCurrentWeather(double latitude, double longitude)
    {
        repository.getCurrentWeather(latitude, longitude);

        return repository.getWeatherResponseLiveData();
    }


    public LiveData<NetworkState> getNetworkState()
    {
        return repository.getNetworkStateLiveData();
    }

    @Override
    protected void onCleared()
    {
        super.onCleared();
        repository.getDisposable().dispose();
    }
}
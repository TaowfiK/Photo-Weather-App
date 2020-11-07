package com.example.photoweather.repositories;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.photoweather.SingleLiveData;
import com.example.photoweather.models.weather.CurrentWeatherResponse;
import com.example.photoweather.networking.ApiService;
import com.example.photoweather.networking.NetworkState;
import com.example.photoweather.networking.RetrofitClient;

import io.reactivex.SingleObserver;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MainRepository
{
    private static final String TAG = "MainRepository";
    private final MutableLiveData<CurrentWeatherResponse> weatherResponseLiveData;
    private final ApiService apiService;
    private final CompositeDisposable disposable;
    private final SingleLiveData<NetworkState> networkStateLiveData;

    public MainRepository()
    {
        weatherResponseLiveData = new MutableLiveData<>();
        apiService = RetrofitClient.getInstance().create(ApiService.class);
        networkStateLiveData = new SingleLiveData<>();
        disposable = new CompositeDisposable();
    }

    public void getCurrentWeather(double latitude, double longitude)
    {
        networkStateLiveData.postValue(NetworkState.LOADING);
        apiService.getCurrentWeather(latitude, longitude, "1b632eabef34a92fd3fbb4920355d865")
                .subscribeOn(Schedulers.io())
                .subscribe(new SingleObserver<CurrentWeatherResponse>()
                {
                    @Override
                    public void onSubscribe(@NonNull Disposable d)
                    {
                        disposable.add(d);
                    }

                    @Override
                    public void onSuccess(@NonNull CurrentWeatherResponse response)
                    {
                        networkStateLiveData.postValue(NetworkState.SUCCESS);
                        weatherResponseLiveData.postValue(response);
                    }

                    @Override
                    public void onError(@NonNull Throwable e)
                    {
                        networkStateLiveData.postValue(NetworkState.FAILED);
                        Log.e(TAG, "onError: ", e);
                    }
                });
    }

    public CompositeDisposable getDisposable()
    {
        return disposable;
    }

    public LiveData<CurrentWeatherResponse> getWeatherResponseLiveData()
    {
        return weatherResponseLiveData;
    }

    public LiveData<NetworkState> getNetworkStateLiveData()
    {
        return networkStateLiveData;
    }
}



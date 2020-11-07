package com.example.photoweather.networking;

import com.example.photoweather.models.weather.CurrentWeatherResponse;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService
{
    @GET("weather")
    Single<CurrentWeatherResponse> getCurrentWeather(@Query("lat") double lat,
                                                     @Query("lon") double lon, @Query("appid")String apiKey);
}

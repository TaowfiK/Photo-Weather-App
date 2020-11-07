package com.example.photoweather.models.weather;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CurrentWeatherResponse {
    @SerializedName("visibility")
    private int visibility;

    @SerializedName("timezone")
    private int timezone;

    @SerializedName("main")
    private Main main;

    @SerializedName("clouds")
    private Clouds clouds;

    @SerializedName("sys")
    private Sys sys;

    @SerializedName("dt")
    private int dt;

    @SerializedName("coord")
    private Coord coord;

    @SerializedName("weather")
    private List<WeatherItem> weather;

    @SerializedName("name")
    private String name;

    @SerializedName("cod")
    private int cod;

    @SerializedName("id")
    private int id;

    @SerializedName("base")
    private String base;

    @SerializedName("wind")
    private Wind wind;

    public int getVisibility()
    {
        return visibility;
    }

    public int getTimezone()
    {
        return timezone;
    }

    public Main getMain()
    {
        return main;
    }

    public Clouds getClouds()
    {
        return clouds;
    }

    public Sys getSys()
    {
        return sys;
    }

    public int getDt()
    {
        return dt;
    }

    public Coord getCoord()
    {
        return coord;
    }

    public List<WeatherItem> getWeather()
    {
        return weather;
    }

    public String getName()
    {
        return name;
    }

    public int getCod()
    {
        return cod;
    }

    public int getId()
    {
        return id;
    }

    public String getBase()
    {
        return base;
    }

    public Wind getWind()
    {
        return wind;
    }

    public String celsius()
    {
        double celsius = calculateCelsius(main.getTemp());

        return celsius + " \u2103";
    }

    public String maxTemp()
    {
        double maxTemp = calculateCelsius(main.getTempMax());

        return maxTemp + " \u2103";
    }

    public String minTemp()
    {
        double minTemp = calculateCelsius(main.getTempMin());

        return minTemp + " \u2103";
    }

    private double calculateCelsius(double temp)
    {
        return (temp - 32) / 1.8;
    }


    @Override
    public String toString()
    {
        return
                "CurrentWeatherResponse{" +
                        "visibility = '" + visibility + '\'' +
                        ",timezone = '" + timezone + '\'' +
                        ",main = '" + main + '\'' +
                        ",clouds = '" + clouds + '\'' +
                        ",sys = '" + sys + '\'' +
                        ",dt = '" + dt + '\'' +
                        ",coord = '" + coord + '\'' +
                        ",weather = '" + weather + '\'' +
                        ",name = '" + name + '\'' +
                        ",cod = '" + cod + '\'' +
                        ",id = '" + id + '\'' +
                        ",base = '" + base + '\'' +
                        ",wind = '" + wind + '\'' +
                        "}";
    }
}

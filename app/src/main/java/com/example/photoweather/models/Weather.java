package com.example.photoweather.models;

public class Weather {

    private String date;
    private String country;
    private String city;
    private String temprature;
    private String minTemprature;
    private String maxnTemprature;
    private String weatherCondition;

    public Weather(String date, String country, String city,
                   String temprature, String minTemprature, String maxnTemprature, String weatherCondition) {
        this.date = date;
        this.country = country;
        this.city = city;
        this.temprature = temprature;
        this.minTemprature = minTemprature;
        this.maxnTemprature = maxnTemprature;
        this.weatherCondition = weatherCondition;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getTemprature() {
        return temprature;
    }

    public void setTemprature(String temprature) {
        this.temprature = temprature;
    }

    public String getMinTemprature() {
        return minTemprature;
    }

    public void setMinTemprature(String minTemprature) {
        this.minTemprature = minTemprature;
    }

    public String getMaxnTemprature() {
        return maxnTemprature;
    }

    public void setMaxnTemprature(String maxnTemprature) {
        this.maxnTemprature = maxnTemprature;
    }

    public String getWeatherCondition() {
        return weatherCondition;
    }

    public void setWeatherCondition(String weatherCondition) {
        this.weatherCondition = weatherCondition;
    }
}

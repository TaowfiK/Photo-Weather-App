package com.example.photoweather.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

import static android.content.ContentValues.TAG;

public class PrefUtils {

    public PrefUtils() {
    }

    private static SharedPreferences getSharedPreferences(Context context) {
        return context.getSharedPreferences("APP_PREF", Context.MODE_PRIVATE);
    }

    public static void storeApiKey(Context context, String apiKey) {
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putString("API_KEY", apiKey);
        editor.commit();
    }

    public static String getApiKey(Context context) {
        return getSharedPreferences(context).getString("API_KEY", null);
    }

    public static String getDate(int dateInMilliSeconds){
        long validDateInMilliSeconds = (long) dateInMilliSeconds * 1000;
        if (validDateInMilliSeconds != 0) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd MMMM yyyy");
            String dateString = simpleDateFormat.format(validDateInMilliSeconds);
            return dateString;
        }else {
            return null;
        }
    }

    @SuppressLint("DefaultLocale")
    public static String getTemperatureInCelsius(String temperatureInKelvin){
        Log.d(TAG, "getTemperatureInCelsius: " + temperatureInKelvin);

        if ( !temperatureInKelvin.equals("null") && !temperatureInKelvin.equals("0.0")) {

            String celsiusSymbol = temperatureInKelvin.substring(temperatureInKelvin.length() - 3);
            temperatureInKelvin = temperatureInKelvin.substring(0, temperatureInKelvin.length() - 3);
            double temperatureInKelvinDouble = Double.parseDouble(temperatureInKelvin);

            return new DecimalFormat("##.##").format(temperatureInKelvinDouble - 273.15) + celsiusSymbol;

        } else {
            return null;
        }
    }
}

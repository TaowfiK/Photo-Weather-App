package com.example.photoweather.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class PrefUtils {

    public PrefUtils() {
    }

    @TypeConverter
    public static String BitMapToString(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] b = baos.toByteArray();
        String temp = Base64.encodeToString(b, Base64.DEFAULT);
        if (temp == null) {
            return null;
        } else
            return temp;
    }

    @TypeConverter
    public static Bitmap StringToBitMap(String encodedString) {
        try {
            byte[] encodeByte = Base64.decode(encodedString, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            if (bitmap == null) {
                return null;
            } else {
                return bitmap;
            }

        } catch (Exception e) {
            e.getMessage();
            return null;
        }
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
            double temperatureInKelvinDouble;
            if (temperatureInKelvin.indexOf('℃') != -1){
                String celsiusSymbol = temperatureInKelvin.substring(temperatureInKelvin.length() - 2);

                temperatureInKelvin = temperatureInKelvin.substring(0, temperatureInKelvin.length() - 2);
                Log.d(TAG, "getTemperatureInCelsius: symbol : " + celsiusSymbol);
                temperatureInKelvinDouble = Double.parseDouble(temperatureInKelvin);
                return new DecimalFormat("##.##").format(temperatureInKelvinDouble - 273.15) + celsiusSymbol;

            }else{
                Log.d(TAG, "getTemperatureInCelsius: temprature : " + temperatureInKelvin);
                temperatureInKelvinDouble = Double.parseDouble(temperatureInKelvin);

                return new DecimalFormat("##.##").format(temperatureInKelvinDouble - 273.15);
            }
        } else {
            return null;
        }
    }
}

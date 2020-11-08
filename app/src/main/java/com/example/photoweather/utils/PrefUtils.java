package com.example.photoweather.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import androidx.room.TypeConverter;

import java.io.ByteArrayOutputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

public class PrefUtils {

    public static String getDate(int dateInMilliSeconds) {
        long validDateInMilliSeconds = (long) dateInMilliSeconds * 1000;
        if (validDateInMilliSeconds != 0) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd MMMM yyyy");
            return simpleDateFormat.format(validDateInMilliSeconds);
        } else {
            return null;
        }
    }

    public static String getTime(int dateInMilliSeconds)
    {
        long validTimeInMilliSeconds = (long) dateInMilliSeconds * 1000;
        if (validTimeInMilliSeconds != 0) {
            SimpleDateFormat simpleTimeFormat = new SimpleDateFormat("HH:mm");
            return simpleTimeFormat.format(validTimeInMilliSeconds);
        } else {
            return null;
        }
    }


}

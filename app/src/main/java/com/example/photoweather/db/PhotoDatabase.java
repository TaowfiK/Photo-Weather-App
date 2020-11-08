package com.example.photoweather.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.photoweather.models.Photo;

@Database(entities = {Photo.class}, version = 2)
public abstract class PhotoDatabase extends RoomDatabase {

    //singleton
    public static PhotoDatabase instance;

    public abstract PhotoDao photoDao();

    public static synchronized PhotoDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context,
                    PhotoDatabase.class, "photo_database")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }

}

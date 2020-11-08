package com.example.photoweather.db;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.photoweather.models.Photo;

@Database(entities = {Photo.class}, version = 1)
public abstract class PhotoDatabase extends RoomDatabase {

    //singleton
    public static PhotoDatabase instance;

    public abstract PhotoDao photoDao();

    public static synchronized PhotoDatabase getInstance(Context context){
        if (instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    PhotoDatabase.class, "photo_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }
        return instance;
    }

    private static Callback roomCallback = new Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new populateDbAsyncTask(instance).execute();
        }
    };

    private static class populateDbAsyncTask extends AsyncTask<Void, Void, Void> {

        private PhotoDao photoDao;

        private populateDbAsyncTask(PhotoDatabase PhotoDatabase) {
            photoDao = PhotoDatabase.photoDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            Log.d("Tawfik", "doInBackground: insert is done ");
            photoDao.insert(new Photo("null", "21 oct 1994", "12:45"));
            return null;
        }
    }
}

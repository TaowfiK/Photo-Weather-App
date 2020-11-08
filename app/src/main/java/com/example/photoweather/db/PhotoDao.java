package com.example.photoweather.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.photoweather.models.Photo;

import java.util.List;

@Dao
public interface PhotoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Photo photo);

    @Query("SELECT * FROM photo_table")
    LiveData<List<Photo>> getAllphotos();
}

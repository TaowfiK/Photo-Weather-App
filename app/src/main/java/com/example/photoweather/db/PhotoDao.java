package com.example.photoweather.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.photoweather.models.Photo;

import java.util.List;

@Dao
public interface PhotoDao {

    @Insert
    void insert(Photo photo);

    @Update
    void update(Photo photo);

    @Delete
    void delete(Photo photo);

    @Query("DELETE FROM photo_table")
    void deleteAllphotos();

    @Query("SELECT * FROM photo_table ORDER BY date DESC")
    LiveData<List<Photo>> getAllphotos();

    @Query("SELECT * FROM photo_table where id = :photoId")
    List<Photo> getPhotoByPhotoId(int photoId);

}

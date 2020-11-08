package com.example.photoweather.models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "photo_table")
public class Photo {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String photo;

    private String date;

    private String time;

    public Photo(String photo, String date, String time) {
        this.photo = photo;
        this.date = date;
        this.time = time;
    }

    public Photo(){
    }

    public int getId(){ return id; }

    public void setId(int id) {
        this.id = id;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}

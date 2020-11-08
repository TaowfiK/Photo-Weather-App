package com.example.photoweather.viewmodels;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.photoweather.models.Photo;
import com.example.photoweather.repositories.PhotoRepository;

import java.util.List;

public class PhotoViewModel extends AndroidViewModel {

    private PhotoRepository photoRepository;
    private LiveData<List<Photo>> allphoto;

    public PhotoViewModel(Application application){
        super(application);
        photoRepository = new PhotoRepository(application);
        allphoto = photoRepository.getAllphoto();
    }

    public void insert(Photo Photo) { photoRepository.insert(Photo); }

    public void update(Photo Photo) { photoRepository.update(Photo); }

    public void delete(Photo Photo) { photoRepository.delete(Photo); }

    public void deleteAllphoto(Photo Photo) { photoRepository.deleteAllPhoto(); }

    public LiveData<List<Photo>> getAllphoto() { return allphoto; }

}

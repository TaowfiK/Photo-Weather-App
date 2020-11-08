package com.example.photoweather.repositories;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.photoweather.db.PhotoDao;
import com.example.photoweather.db.PhotoDatabase;
import com.example.photoweather.models.Photo;

import java.util.List;


public class PhotoRepository {

    private PhotoDao photoDao;
    private LiveData<List<Photo>> allPhotos;

    public PhotoRepository (Application application){
        PhotoDatabase photoDatabase = PhotoDatabase.getInstance(application);
        photoDao = photoDatabase.photoDao();
        allPhotos = photoDao.getAllphotos();
    }

    public void insert(Photo photo){
        new InsertPhotoAsyncTask(photoDao).execute(photo);
    }

    public void update(Photo photo){
        new updatePhotoAsyncTask(photoDao).execute(photo);
    }

    public void delete(Photo photo){
        new deletePhotoAsyncTask(photoDao).execute(photo);
    }

    public void deleteAllPhoto(){
        new deleteAllPhotosAsyncTask(photoDao).execute();
    }

    public LiveData<List<Photo>> getAllphoto(){
        return allPhotos;
    }

    private static class InsertPhotoAsyncTask extends AsyncTask<Photo, Void, Void> {

        private PhotoDao photoDao;

        public InsertPhotoAsyncTask(PhotoDao photoDao) {
            this.photoDao = photoDao;
        }

        @Override
        protected Void doInBackground(Photo... photos) {
            photoDao.insert(photos[0]);
            return null;
        }
    }

    private static class updatePhotoAsyncTask extends AsyncTask<Photo, Void, Void>{
        private PhotoDao photoDao;

        public updatePhotoAsyncTask(PhotoDao photoDao) {
            this.photoDao = photoDao;
        }

        @Override
        protected Void doInBackground(Photo... photos) {
            photoDao.update(photos[0]);
            return null;
        }
    }

    private static class deletePhotoAsyncTask extends AsyncTask<Photo, Void, Void>{
        private PhotoDao photoDao;

        public deletePhotoAsyncTask(PhotoDao photoDao) {
            this.photoDao = photoDao;
        }

        @Override
        protected Void doInBackground(Photo... photos) {
            photoDao.delete(photos[0]);
            return null;
        }
    }

    private static class deleteAllPhotosAsyncTask extends AsyncTask<Void, Void, Void>{
        private PhotoDao photoDao;

        public deleteAllPhotosAsyncTask(PhotoDao photoDao) {
            this.photoDao = photoDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            photoDao.deleteAllphotos();
            return null;
        }
    }

}

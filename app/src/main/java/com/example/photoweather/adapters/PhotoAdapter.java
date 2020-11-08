package com.example.photoweather.adapters;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.photoweather.R;
import com.example.photoweather.models.Photo;

import java.util.ArrayList;
import java.util.List;

public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.PhotoHolder> {

    private List<Photo> allPhotos = new ArrayList<>();
    private onPhotoClickListener onPhotoClickListener;

    public void setOnPhotoClickListener(PhotoAdapter.onPhotoClickListener onPhotoClickListener) {
        this.onPhotoClickListener = onPhotoClickListener;
    }

    @NonNull
    @Override
    public PhotoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_history_item, parent, false);
        return new PhotoHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PhotoHolder holder, int position) {
        Photo currentPhotoItem = allPhotos.get(position);
        holder.photoImageView.setImageDrawable(new BitmapDrawable(convertBase64ToBitmap(currentPhotoItem.getPhoto())));
        holder.photoDateTextView.setText(currentPhotoItem.getDate());
        holder.photoTimeTextView.setText(currentPhotoItem.getTime());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onPhotoClickListener.onPhotoClickListener(convertBase64ToBitmap(currentPhotoItem.getPhoto()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return allPhotos.size();
    }

    public void setPhotos(List<Photo> photos) {
        this.allPhotos = photos;
        notifyDataSetChanged();
    }

    class PhotoHolder extends RecyclerView.ViewHolder {

        private ImageView photoImageView;
        private TextView photoDateTextView;
        private TextView photoTimeTextView;

        public PhotoHolder(@NonNull View itemView) {
            super(itemView);
            photoImageView = itemView.findViewById(R.id.photo_image_viw);
            photoDateTextView = itemView.findViewById(R.id.photo_date_text_view);
            photoTimeTextView = itemView.findViewById(R.id.photo_time_text_view);


        }
    }

    private Bitmap convertBase64ToBitmap(String encryptedImage) {
        byte[] decodedBytes = Base64.decode(
                encryptedImage.substring(encryptedImage.indexOf(",") + 1),
                Base64.DEFAULT
        );

        return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
    }

    public interface onPhotoClickListener {
        void onPhotoClickListener(Bitmap encryptedImage);
    }

}

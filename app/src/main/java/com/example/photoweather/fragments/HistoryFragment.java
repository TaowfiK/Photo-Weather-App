package com.example.photoweather.fragments;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.photoweather.MainActivity;
import com.example.photoweather.adapters.PhotoAdapter;
import com.example.photoweather.databinding.FragmentHistoryItemBindingImpl;
import com.example.photoweather.databinding.FragmentHistoryListBinding;
import com.example.photoweather.databinding.FragmentHomeBinding;
import com.example.photoweather.models.Photo;
import com.example.photoweather.viewmodels.HomeViewModel;
import com.example.photoweather.viewmodels.PhotoViewModel;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;

public class HistoryFragment extends Fragment {

    private FragmentHistoryListBinding binding;
    private PhotoViewModel photoViewModel;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        binding = FragmentHistoryListBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        binding.photoList.setLayoutManager(new LinearLayoutManager(getContext()));

        PhotoAdapter noteAdapter = new PhotoAdapter();
        binding.photoList.setAdapter(noteAdapter);
        Log.d("Tawfik", "onViewCreated: History View Created");
        photoViewModel = new ViewModelProvider(this).get(PhotoViewModel.class);
        Log.d("Tawfik", "onViewCreated: View Model Created");



        photoViewModel.getAllphoto().observe(requireActivity(), new Observer<List<Photo>>() {
            @Override
            public void onChanged(List<Photo> notes) {
                //change Recycle view Data
                noteAdapter.setPhotos(notes);
            }
        });

    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == 1 && resultCode == RESULT_OK) {
//            imageFragmentContainer.setVisibility(View.VISIBLE);
//            bitmaps = new ArrayList<>();
//            imageSources = new ArrayList<>();
//            ClipData clipData = data.getClipData();
//            //clip data will be null if user select one item from gallery
//
//            if (clipData != null) {
//                for (int i = 0; i < clipData.getItemCount(); i++) {
//                    Uri imageUri = clipData.getItemAt(i).getUri();
//                    try {
//                        InputStream is = getContentResolver().openInputStream(imageUri);
//                        Bitmap bitmap = BitmapFactory.decodeStream(is);
//                        bitmaps.add(bitmap);
//                        String imageSource = ImageBitmapString.BitMapToString(bitmap);
//                        imageSources.add(imageSource);
//                    } catch (FileNotFoundException e) {
//                        e.printStackTrace();
//                    }
//
//                }
//            } else {
//                Uri imageUri = data.getData();
//                try {
//                    InputStream is = getContentResolver().openInputStream(imageUri);
//                    Bitmap bitmap = BitmapFactory.decodeStream(is);
//                    bitmaps.add(bitmap);
//                    String imageSource = ImageBitmapString.BitMapToString(bitmap);
//                    imageSources.add(imageSource);
//                } catch (FileNotFoundException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//    }
//

    private void loadImagesFromGallery() {

        if (ActivityCompat.checkSelfPermission(getContext(),
                Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    100);
            return;
        }
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        intent.setType("image/*");
        startActivityForResult(intent, 1);
    }
}

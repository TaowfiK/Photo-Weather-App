package com.example.photoweather.fragments;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.photoweather.R;
import com.example.photoweather.adapters.PhotoAdapter;
import com.example.photoweather.databinding.FragmentHistoryListBinding;
import com.example.photoweather.db.PhotoDatabase;
import com.example.photoweather.models.Photo;

import java.util.List;

public class HistoryFragment extends Fragment {

    private FragmentHistoryListBinding binding;
    private NavController navController;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHistoryListBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.photoList.setLayoutManager(new LinearLayoutManager(getContext()));
        navController = Navigation.findNavController(requireActivity(), R.id.fragment);

        PhotoAdapter photoAdapter = new PhotoAdapter();
        PhotoDatabase photoDatabase = PhotoDatabase.getInstance(requireContext());
        photoDatabase.photoDao().getAllphotos().observe(getViewLifecycleOwner(), new Observer<List<Photo>>() {
            @Override
            public void onChanged(List<Photo> photos) {
                photoAdapter.setPhotos(photos);
                binding.photoList.setAdapter(photoAdapter);
            }
        });

        photoAdapter.setOnPhotoClickListener(new PhotoAdapter.onPhotoClickListener() {
            @Override
            public void onPhotoClickListener(Bitmap bitmap) {
                Bundle bundle = new Bundle();
                bundle.putParcelable("bitmap", bitmap);
                PhotoFragment photoFragment = new PhotoFragment();
                photoFragment.setArguments(bundle);
                navController.navigate(R.id.photoFragment, bundle);
            }
        });


    }

}

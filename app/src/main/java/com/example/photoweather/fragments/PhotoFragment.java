package com.example.photoweather.fragments;

import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.photoweather.R;
import com.example.photoweather.databinding.FragmentPhotoBinding;

public class PhotoFragment extends Fragment {

    private FragmentPhotoBinding binding;
    private NavController navController;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentPhotoBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        navController = Navigation.findNavController(requireActivity(), R.id.fragment);

        if (getArguments() != null) {
            Bitmap bitmap = getArguments().getParcelable("bitmap");
            binding.selectedPhotoImageView.setImageBitmap(bitmap);
        }

        binding.backButtonImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.popBackStack();
            }
        });
        super.onViewCreated(view, savedInstanceState);

    }
}
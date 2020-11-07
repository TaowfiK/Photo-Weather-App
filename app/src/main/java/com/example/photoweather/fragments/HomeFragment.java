package com.example.photoweather.fragments;

import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Looper;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.photoweather.databinding.FragmentHomeBinding;
import com.example.photoweather.models.weather.CurrentWeatherResponse;
import com.example.photoweather.networking.NetworkState;
import com.example.photoweather.viewmodels.HomeViewModel;
import com.example.photoweather.R;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import io.reactivex.android.BuildConfig;

public class HomeFragment extends Fragment {

    private static final String TAG = "HomeFragment";

    private HomeViewModel mViewModel;
    private FragmentHomeBinding binding;

    private static final long UPDATE_INTERVAL_IN_MILLISECONDS = 10000;

    private static final long FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS = 5000;

    private static final int REQUEST_CHECK_SETTINGS = 100;

    private FusedLocationProviderClient mFusedLocationClient;
    private SettingsClient mSettingsClient;
    private LocationRequest mLocationRequest;
    private LocationSettingsRequest mLocationSettingsRequest;
    private LocationCallback mLocationCallback;
    private Location mCurrentLocation;
    private Boolean mRequestingLocationUpdates;


    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

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
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        setPhotoBackground();
        initLocationService();
        requestPermissions();
        mViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        networkState();
    }
    private void networkState()
    {
        mViewModel.getNetworkState().observe(getViewLifecycleOwner(), new Observer<NetworkState>()
        {
            @Override
            public void onChanged(NetworkState networkState)
            {
                if (networkState.getState() == NetworkState.State.LOADING)
                {
                    binding.setHideProgress(false);
                } else
                {
                    binding.setHideProgress(true);
                }
            }
        });
    }

    private void initLocationService()
    {
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity());
        mSettingsClient = LocationServices.getSettingsClient(requireActivity());

        mLocationCallback = new LocationCallback()
        {
            @Override
            public void onLocationResult(LocationResult locationResult)
            {
                mCurrentLocation = locationResult.getLastLocation();
                updateUI();
                super.onLocationResult(locationResult);
            }
        };

        mRequestingLocationUpdates = false;

        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(UPDATE_INTERVAL_IN_MILLISECONDS);
        mLocationRequest.setFastestInterval(FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
        builder.addLocationRequest(mLocationRequest);
        mLocationSettingsRequest = builder.build();
    }

    private void updateUI()
    {
        if (mCurrentLocation != null)
        {
            if (mCurrentLocation.getLatitude() != 0 && mCurrentLocation.getLongitude() != 0)
            {
                mViewModel.getCurrentWeather(mCurrentLocation.getLatitude(), mCurrentLocation.getLongitude())
                        .observe(getViewLifecycleOwner(), new Observer<CurrentWeatherResponse>()
                        {
                            @Override
                            public void onChanged(CurrentWeatherResponse response)
                            {
                                binding.setWeather(response);
                            }
                        });
            }
        }

    }

    private void startLocationListener()
    {
        mSettingsClient.checkLocationSettings(mLocationSettingsRequest)
                .addOnSuccessListener(requireActivity(), new OnSuccessListener<LocationSettingsResponse>()
                {
                    @SuppressLint("MissingPermission")
                    @Override
                    public void onSuccess(LocationSettingsResponse locationSettingsResponse)
                    {
                        Log.d(TAG, "onSuccess: Location listener is started");

                        mFusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper());
                        updateUI();
                    }
                }).addOnFailureListener(requireActivity(), new OnFailureListener()
        {
            @Override
            public void onFailure(@NonNull Exception e)
            {
                int statusCode = ((ApiException) e).getStatusCode();
                switch (statusCode)
                {
                    case LocationSettingsStatusCodes
                            .RESOLUTION_REQUIRED:
                        Log.d(TAG, "onFailure: Location settings are not satisfied. Attempting to upgrade location settings ");
                        try
                        {
                            ResolvableApiException rae = (ResolvableApiException) e;
                            rae.startResolutionForResult(requireActivity(), REQUEST_CHECK_SETTINGS);
                        } catch (IntentSender.SendIntentException sendIntentException)
                        {
                            sendIntentException.printStackTrace();
                        }

                        break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        String errorMessage = "Location settings are inadequate, and cannot be fixed here. Fix in Settings.";
                        Log.e(TAG, "onFailure: " + errorMessage);
                        Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show();
                }

                updateUI();
            }
        });
    }

    public void requestPermissions()
    {
        Dexter.withActivity(requireActivity())
                .withPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                .withListener(new PermissionListener()
                {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response)
                    {
                        mRequestingLocationUpdates = true;
                        startLocationListener();
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response)
                    {
                        if (response.isPermanentlyDenied())
                        {
                            openSettings();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token)
                    {
                        token.continuePermissionRequest();
                    }
                }).check();
    }

    private void openSettings()
    {
        Intent intent = new Intent();
        intent.setAction(
                Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package",
                BuildConfig.APPLICATION_ID, null);
        intent.setData(uri);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }


    private void setPhotoBackground()
    {
        if (getArguments() != null)
        {
            Bitmap bitmap = getArguments().getParcelable("bitmap");
            binding.photoBackground.setImageBitmap(bitmap);
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode)
        {
            case REQUEST_CHECK_SETTINGS:
                switch (resultCode)
                {
                    case Activity.RESULT_OK:
                        Log.e(TAG, "User agreed to make required location settings changes.");
                        break;
                    case Activity.RESULT_CANCELED:
                        Log.e(TAG, "User chose not to make required location settings changes.");
                        mRequestingLocationUpdates = false;
                        break;
                }
                break;
        }
    }
//
//    @Override
//    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater)
//    {
//        super.onCreateOptionsMenu(menu, inflater);
//        inflater.inflate(R.menu.main_menu, menu);
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item)
//    {
//        if (item.getItemId() == R.id.share)
//        {
//            shareBitmap(requireActivity(), takeScreenShotForLayout());
//        }
//        return super.onOptionsItemSelected(item);
//    }

    private void shareBitmap(Activity requireActivity, Bitmap bitmap)
    {
        createDirectoryAndSaveImage(requireActivity, bitmap);
        getImageUriAndShare(requireActivity);

    }

    private void getImageUriAndShare(Activity requireActivity)
    {
        try
        {

            Uri contentUri = getImageContentUri(requireActivity);

            if (contentUri != null)
            {
                shareImage(contentUri, requireActivity);

            }
        } catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    private void shareImage(Uri contentUri, Activity requireActivity)
    {
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        shareIntent.setDataAndType(contentUri, requireActivity.getContentResolver().getType(contentUri));
        shareIntent.putExtra(Intent.EXTRA_STREAM, contentUri);
        startActivity(Intent.createChooser(shareIntent, "Share with..."));
    }

    private Uri getImageContentUri(Activity requireActivity)
    {
        File imagePath = new File(requireActivity.getCacheDir(), "images");
        File newFile = new File(imagePath, "image.png");
        return FileProvider.getUriForFile(requireContext(), "com.tutorial.openweather.provider", newFile);
    }

    private void createDirectoryAndSaveImage(Activity requireActivity, Bitmap bitmap)
    {
        try
        {

            File cachePath = new File(requireActivity.getCacheDir(), "images");
            cachePath.mkdirs(); // don't forget to make the directory
            FileOutputStream stream = new FileOutputStream(cachePath + "/image.png"); // overwrites this image every time
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            stream.close();

        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

//    private Bitmap takeScreenShotForLayout()
//    {
//        try
//        {
//            View rootView = getActivity().getWindow().getDecorView().findViewById(R.id.root_layout);
//            rootView.setDrawingCacheEnabled(true);
//            return rootView.getDrawingCache();
//        } catch (Exception ex)
//        {
//            ex.printStackTrace();
//        }
//
//        return null;
//    }

    @Override
    public void onResume()
    {
        super.onResume();
        if (mRequestingLocationUpdates && checkPermissions())
        {
            startLocationListener();
        }

        updateUI();
    }

    @Override
    public void onPause()
    {
        super.onPause();
        if (mRequestingLocationUpdates)
        {
            // pausing location updates
            stopLocationUpdates();
        }
    }

    private boolean checkPermissions()
    {
        int permissionState = ActivityCompat.checkSelfPermission(requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION);
        return permissionState == PackageManager.PERMISSION_GRANTED;
    }

    public void stopLocationUpdates()
    {
        mFusedLocationClient
                .removeLocationUpdates(mLocationCallback)
                .addOnCompleteListener(requireActivity(), new OnCompleteListener<Void>()
                {
                    @Override
                    public void onComplete(@NonNull Task<Void> task)
                    {
                        Toast.makeText(requireContext(), "Location updates stopped!", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
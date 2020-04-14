package com.example.concordia_campus_guide.helper;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;

import androidx.core.app.ActivityCompat;

import com.example.concordia_campus_guide.fragments.LocationFragment;

import static androidx.core.content.ContextCompat.checkSelfPermission;

public class CurrentLocationPermissionRequest {

    public static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;

    /**
     * The purpose of this application is to ask the user for their permission
     * of using their current location.
     */
    public void getLocationPermission(LocationFragment locationFragment) {
        String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION};

        if (requestPermission(locationFragment.getContext())) {
            locationFragment.initMap();
            return;
        } else {
            ActivityCompat.requestPermissions(locationFragment.getActivity(),
                    permissions,
                    LOCATION_PERMISSION_REQUEST_CODE);
        }
        locationFragment.initMap();
    }
    /**
     * @return the method returns true if the user accepts to give the application permission
     * for using their current location.
     */
    public boolean requestPermission(Context context) {
        return (checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED);
    }


}

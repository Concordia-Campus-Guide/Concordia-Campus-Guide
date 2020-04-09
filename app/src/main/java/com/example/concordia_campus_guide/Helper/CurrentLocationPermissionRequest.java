package com.example.concordia_campus_guide.Helper;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;

import com.example.concordia_campus_guide.ClassConstants;
import com.example.concordia_campus_guide.Fragments.LocationFragment;
import com.example.concordia_campus_guide.Fragments.LocationFragment;

import androidx.core.app.ActivityCompat;

import static androidx.core.content.ContextCompat.checkSelfPermission;

public class CurrentLocationPermissionRequest {

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
                    ClassConstants.LOCATION_PERMISSION_REQUEST_CODE);
        }
        locationFragment.initMap();
    }
    /**
     * @return the method returns true if the user accepts to give the application permission
     * for using their current location.
     */
    private boolean requestPermission(Context context) {
        return (checkSelfPermission(context, ClassConstants.FINE_LOCATION) == PackageManager.PERMISSION_GRANTED);
    }


}

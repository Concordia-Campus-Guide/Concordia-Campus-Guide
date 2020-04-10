package com.example.concordia_campus_guide.Helper;

import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Location;
import android.os.Handler;


import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;


public class CurrentLocation {
    private Location currentLocation;
    private FusedLocationProviderClient fusedLocationProviderClient;


    public FusedLocationProviderClient getFusedLocationProviderClient() {
        return fusedLocationProviderClient;
    }

    public CurrentLocation(Context context){
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context);
        currentLocation = null;
    }

    public void setCurrentLocation(Location location) {
        this.currentLocation = location;
    }

    public Location getCurrentLocation() {
        fusedLocationProviderClient.getLastLocation().addOnSuccessListener(location -> {
            if (location != null) {
                setCurrentLocation(location);
            }
        });
        return currentLocation;
    }

    public void updateLocationEvery5Seconds() {
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @SuppressLint("MissingPermission")
            @Override
            public void run() {
                fusedLocationProviderClient.getLastLocation().addOnSuccessListener(location -> {
                    if (location != null) {
                        setCurrentLocation(location);
                    }
                });
                handler.postDelayed(this, 2000);
            }
        }, 2000);
    }

}

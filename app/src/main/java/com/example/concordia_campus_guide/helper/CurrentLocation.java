package com.example.concordia_campus_guide.helper;

import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Location;
import android.os.Handler;

import com.example.concordia_campus_guide.global.SelectingToFromState;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

public class CurrentLocation {
    private Location myLocation;
    private FusedLocationProviderClient fusedLocationProviderClient;

    public FusedLocationProviderClient getFusedLocationProviderClient() {
        return fusedLocationProviderClient;
    }

    public CurrentLocation(Context context){
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context);
        myLocation = null;
    }

    public void setMyLocation(Location location) {
        this.myLocation = location;
    }

    public Location getMyLocation() {
        fusedLocationProviderClient.getLastLocation().addOnSuccessListener(location -> {
            if (location != null) {
                setMyLocation(location);
            }
        });
        return myLocation;
    }

    public void updateLocationEvery5Seconds() {
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @SuppressLint("MissingPermission")
            @Override
            public void run() {
                fusedLocationProviderClient.getLastLocation().addOnSuccessListener(location -> {
                    if (location != null) {
                        setMyLocation(location);
                        SelectingToFromState.setMyCurrentLocation(location);
                    }
                });
                handler.postDelayed(this, 500);
            }
        }, 500);
    }

}

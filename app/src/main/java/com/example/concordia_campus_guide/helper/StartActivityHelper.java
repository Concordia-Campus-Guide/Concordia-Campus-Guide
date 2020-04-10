package com.example.concordia_campus_guide.helper;

import android.app.Activity;
import android.content.Intent;
import android.location.Location;

import com.example.concordia_campus_guide.activities.RoutesActivity;
import com.example.concordia_campus_guide.global.SelectingToFromState;
import com.example.concordia_campus_guide.models.MyCurrentPlace;
import com.example.concordia_campus_guide.models.Place;

public class StartActivityHelper {
    public static  void openRoutesPage(Place place, Activity activity){
        Intent openRoutes = new Intent(activity.getBaseContext(), RoutesActivity.class);

        if(SelectingToFromState.isQuickSelect()){
            SelectingToFromState.setTo(place);
            if(SelectingToFromState.getMyCurrentLocation() != null){
                Location myCurrentLocation = SelectingToFromState.getMyCurrentLocation();
                SelectingToFromState.setFrom(new MyCurrentPlace(activity.getBaseContext(), myCurrentLocation.getLongitude(), myCurrentLocation.getLatitude()));
            }
            else{
                SelectingToFromState.setFrom(new MyCurrentPlace(activity.getBaseContext()));
            }
        }
        if(SelectingToFromState.isSelectFrom()){
            SelectingToFromState.setFrom(place);
        }
        if(SelectingToFromState.isSelectTo()){
            SelectingToFromState.setTo(place);
        }
        activity.startActivity(openRoutes);
    }
}

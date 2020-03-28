package com.example.concordia_campus_guide.Helper;

import android.app.Activity;
import android.content.Intent;
import android.location.Location;

import com.example.concordia_campus_guide.Activities.RoutesActivity;
import com.example.concordia_campus_guide.Global.SelectingToFromState;
import com.example.concordia_campus_guide.Models.MyCurrentPlace;
import com.example.concordia_campus_guide.Models.Place;

public class StartActivityHelper {
    public static  void openRoutesPage(Place place, Activity activity){
        Intent openRoutes = new Intent(activity.getBaseContext(), RoutesActivity.class);

        if(SelectingToFromState.isQuickSelect()){
            SelectingToFromState.setTo(place);
            if(SelectingToFromState.getMyCurrentLocation() != null){
                Location myCurrentLocation = SelectingToFromState.getMyCurrentLocation();
                SelectingToFromState.setFrom(new MyCurrentPlace(myCurrentLocation.getLatitude(), myCurrentLocation.getLongitude()));
            }
            else{
                SelectingToFromState.setFrom(new MyCurrentPlace());
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

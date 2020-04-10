package com.example.concordia_campus_guide.models;

import android.content.Context;

import com.example.concordia_campus_guide.R;

public class MyCurrentPlace extends Place {
    String displayName = "";

    public MyCurrentPlace(Context context, Double longitude, Double latitude){
        this.setCenterCoordinates(new Coordinates(latitude, longitude));
        this.displayName = context.getResources().getString(R.string.my_current_location);
    }

    public MyCurrentPlace(Context context){
        this.displayName = context.getResources().getString(R.string.select_location);
    }

    public String getDisplayName(){
        return this.displayName;
    }
}

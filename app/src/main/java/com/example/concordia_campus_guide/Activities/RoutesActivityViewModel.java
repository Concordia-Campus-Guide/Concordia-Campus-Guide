package com.example.concordia_campus_guide.Activities;

import android.location.Location;

import androidx.lifecycle.ViewModel;

import com.example.concordia_campus_guide.Database.AppDatabase;
import com.example.concordia_campus_guide.Models.Place;

public class RoutesActivityViewModel extends ViewModel {

    private AppDatabase appDB;
    private Place from;
    private Place to;

    private Location myCurrentLocation;

    public RoutesActivityViewModel(AppDatabase appDb) {
        this.appDB = appDb;
    }

    public void setTo(Place place){
        this.to = place;
    }

    public Place getTo(){
        return to;
    }

    public Place getFrom(){
        return from;
    }

    public void setFrom(Place from) {
        this.from = from;
    }

}

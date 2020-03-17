package com.example.concordia_campus_guide.Activities;

import android.app.Application;
import android.location.Location;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.concordia_campus_guide.Database.AppDatabase;
import com.example.concordia_campus_guide.Models.Place;
import com.example.concordia_campus_guide.Models.Shuttle;

import java.util.List;

public class RoutesActivityViewModel extends AndroidViewModel {

    private AppDatabase appDB;
    private Place from;
    private Place to;
    private List<Shuttle> shuttles;

    private Location myCurrentLocation;

    public RoutesActivityViewModel(@NonNull Application application) {
        super(application);

        appDB = AppDatabase.getInstance(application.getApplicationContext());
        setShuttles();
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

    public void setShuttles() {
        shuttles = appDB.shuttleDao().getAll();
    }

    public List<Shuttle> getShuttles() {
        return shuttles;
    }
}

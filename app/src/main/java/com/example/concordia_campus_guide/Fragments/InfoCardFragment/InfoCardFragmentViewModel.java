package com.example.concordia_campus_guide.Fragments.InfoCardFragment;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.concordia_campus_guide.Database.AppDatabase;
import com.example.concordia_campus_guide.Models.Building;
import com.example.concordia_campus_guide.Models.Place;

public class InfoCardFragmentViewModel extends AndroidViewModel {

    private Place place;
    private AppDatabase appDb;

    public InfoCardFragmentViewModel(@NonNull Application application) {
        super(application);

        appDb = AppDatabase.getInstance(application.getApplicationContext());
    }

    public Building getBuilding() {
        return (Building) place;
    }

    public void setBuilding(String buildingCode){ this.place = (Place) appDb.buildingDao().getBuildingByBuildingCode(buildingCode); }

    public void setPlace(Place place) {
        this.place = place;
    }
}
package com.example.concordia_campus_guide.ViewModels;

import androidx.lifecycle.ViewModel;

import com.example.concordia_campus_guide.Database.AppDatabase;
import com.example.concordia_campus_guide.Models.Building;
import com.example.concordia_campus_guide.Models.Place;

public class InfoCardFragmentViewModel extends ViewModel {

    private Place place;
    private AppDatabase appDb;

    public InfoCardFragmentViewModel(AppDatabase appDb) {
        this.appDb = appDb;
    }

    public Building getBuilding() {
        return (Building) place;
    }

    public void setBuilding(String buildingCode) {
        this.place = appDb.buildingDao().getBuildingByBuildingCode(buildingCode);
    }

    public void setPlace(Place place) {
        this.place = place;
    }
}
package com.example.concordia_campus_guide.Fragments.SmallInfoCardFragment;

import androidx.lifecycle.ViewModel;

import com.example.concordia_campus_guide.Database.AppDatabase;
import com.example.concordia_campus_guide.Models.Place;

public class SmallInfoCardFragmentViewModel extends ViewModel {

    Place place;
    AppDatabase appDb;

    public SmallInfoCardFragmentViewModel(AppDatabase appDb) {
        this.appDb = appDb;
    }

    public void setPlace(Place place){
        this.place = place;
    }

    public Place getPlace(){
        return this.place;
    }
}

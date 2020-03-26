package com.example.concordia_campus_guide.Fragments.POIFragment;

import com.example.concordia_campus_guide.Database.AppDatabase;
import com.example.concordia_campus_guide.Models.PoiType;
import com.example.concordia_campus_guide.Models.PointType;
import com.example.concordia_campus_guide.Models.WalkingPoint;

import java.util.List;

import androidx.lifecycle.ViewModel;

public class POIFragmentViewModel extends ViewModel {

    private AppDatabase appDb;

    public POIFragmentViewModel(AppDatabase appDb) {
        this.appDb = appDb;
    }

    public List<WalkingPoint> getPOIOfType(@PoiType  String type) {
        return appDb.walkingPointDao().getAllPointsForPointType(type);
    }
}
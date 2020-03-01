package com.example.concordia_campus_guide.Activities;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.concordia_campus_guide.Global.ApplicationState;
import com.example.concordia_campus_guide.Models.Building;
import com.example.concordia_campus_guide.Models.Buildings;

import java.util.ArrayList;
import java.util.List;

public class SearchActivityViewModel extends AndroidViewModel {

    Buildings buildings;

    public SearchActivityViewModel(@NonNull Application application) {
        super(application);
    }

    public Buildings getBuildings() {
        return ApplicationState.getInstance(getApplication().getApplicationContext()).getBuildings();
    }

    public List<Building> getFilteredBuildings(String filter){
        List<Building> unfilteredBuildings = getBuildings().getBuildings();
        List<Building> filteredBuildings = new ArrayList<Building>();

        for(Building building: unfilteredBuildings){
            if(building.getBuilding_Long_Name().startsWith(filter)){
                filteredBuildings.add(building);
            }
        }

        return filteredBuildings;
    }

    public void importBuildings(Context context){
        buildings = ApplicationState.getInstance(context).getBuildings();
    }

}
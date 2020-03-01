package com.example.concordia_campus_guide.InfoCardFragment;

import android.app.Application;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.concordia_campus_guide.Models.Building;

// This class is for the business logic
public class InfoCardFragmentViewModel extends AndroidViewModel {

    private Building building;
    private TextView infoCardTitle;
    private TextView buildingAddress;

    public InfoCardFragmentViewModel(@NonNull Application application) {
        super(application);
    }

    public Building getBuilding() {
        return building;
    }

    public void setBuilding(Building building) {
        this.building = building;
    }

    public TextView getInfoCardTitle() {
        return infoCardTitle;
    }

    public void setInfoCardTitle(TextView infoCardTitle) {
        this.infoCardTitle = infoCardTitle;
    }

    public TextView getBuildingAddress() {
        return buildingAddress;
    }

    public void setBuildingAddress(TextView buildingAddress) {
        this.buildingAddress = buildingAddress;
    }
}
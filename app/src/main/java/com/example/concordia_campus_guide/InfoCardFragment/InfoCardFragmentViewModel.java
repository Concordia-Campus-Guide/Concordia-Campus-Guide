package com.example.concordia_campus_guide.InfoCardFragment;

import android.widget.TextView;
import androidx.lifecycle.ViewModel;

import com.example.concordia_campus_guide.Models.Building;

public class InfoCardFragmentViewModel extends ViewModel {

    private Building building;
    private TextView infoCardTitle;
    private TextView buildingAddress;

    public Building getBuilding() { return building; }
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
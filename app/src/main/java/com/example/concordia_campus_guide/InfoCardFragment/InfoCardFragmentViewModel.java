package com.example.concordia_campus_guide.InfoCardFragment;

import android.app.Application;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.ViewModel;

import com.example.concordia_campus_guide.Models.Building;

// This class is for the business logic
public class InfoCardFragmentViewModel extends ViewModel {

    private Building building;
    private TextView infoCardTitle;
    private TextView buildingAddress;

    public Building getBuilding() {
        return building;

    }

    public void setBuilding(Building building) {
        this.building = building;
    }

    /**
     * Returns infoCardTitle attribute
     *
     * @return infoCardTitle
     */
    public TextView getInfoCardTitle() {
        return infoCardTitle;
    }

    /**
     * Sets infoCardTitle attribute
     *
     * @param infoCardTitle is set to the TextView infoCardTitle attribute
     */
    public void setInfoCardTitle(TextView infoCardTitle) {
        this.infoCardTitle = infoCardTitle;
    }

    /**
     * Returns buildingAddress attribute
     *
     * @return buildingAddress
     */
    public TextView getBuildingAddress() {
        return buildingAddress;
    }

    /**
     * Sets buildingAddress attribute
     *
     * @param buildingAddress is set to the TextView buildingAddress attribute
     */
    public void setBuildingAddress(TextView buildingAddress) {
        this.buildingAddress = buildingAddress;
    }
}
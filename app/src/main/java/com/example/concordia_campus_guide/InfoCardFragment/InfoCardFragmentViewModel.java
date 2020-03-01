package com.example.concordia_campus_guide.InfoCardFragment;

import android.app.Application;
import android.content.Context;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.concordia_campus_guide.Models.Buildings;
import com.example.concordia_campus_guide.R;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;

// This class is for the business logic
public class InfoCardFragmentViewModel extends AndroidViewModel {

    private Buildings buildings;
    private TextView infoCardTitle;
    private TextView buildingAddress;

    /**
     * Constructor
     */
    public InfoCardFragmentViewModel(@NonNull Application application) {
        super(application);
    }

    /**
     * Reads json file and outputs Buildings object, containing many Building objects
     *
     * @return buildings: a object containing many other Building objects, which
     * contain the necessary information for the info card
     */
    public Buildings readJsonFile(Context context){
        String json;
        Buildings buildings = new Buildings();

        try{
            InputStream is = getApplication().getResources().openRawResource(R.raw.buildings_info);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
            buildings = new Gson().fromJson(json, Buildings.class);
        } catch (IOException e){
            e.printStackTrace();
        }

        this.buildings = buildings;

        return buildings;
    }

    /**
     * Returns buildings attribute
     *
     * @return buildings
     */
    public Buildings getBuildings() {
        return buildings;
    }

    /**
     * Sets buildings attribute
     *
     * @param buildings is set to the ViewModel buildings attribute
     */
    public void setBuildings(Buildings buildings) {
        this.buildings = buildings;
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
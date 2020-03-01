package com.example.concordia_campus_guide.Activities;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.concordia_campus_guide.Global.ApplicationState;
import com.example.concordia_campus_guide.Models.Buildings;

public class MainActivityViewModel extends AndroidViewModel {

    private Buildings buildings;

    public MainActivityViewModel(@NonNull Application application) {
        super(application);
    }

    public void importBuildings(Context context){
        buildings = ApplicationState.getInstance(context).getBuildings();
    }

    public Buildings getBuildings(){
        return buildings;
    }
}

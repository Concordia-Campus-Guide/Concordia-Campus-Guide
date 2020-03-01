package com.example.concordia_campus_guide.Global;

import android.content.Context;

import com.example.concordia_campus_guide.Models.Buildings;
import com.example.concordia_campus_guide.R;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;

public class ApplicationState {

    private Context context;
    private static ApplicationState instance;
    private Buildings buildings;

    private ApplicationState(Context context) {
        this.context = context;
        importBuildings();
    }

    public static ApplicationState getInstance(Context context) {
        if (instance == null) {
            instance = new ApplicationState(context);
        }
        return instance;
    }

    private void importBuildings(){
        String json;
        Buildings buildings = new Buildings();

        try{
            InputStream is = context.getResources().openRawResource(R.raw.buildings_info);
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
    }

    public Buildings getBuildings(){
        return this.buildings;
    }


}

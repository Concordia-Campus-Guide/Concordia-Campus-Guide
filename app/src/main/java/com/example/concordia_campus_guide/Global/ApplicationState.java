package com.example.concordia_campus_guide.Global;

import android.content.Context;

import com.example.concordia_campus_guide.Models.Buildings;
import com.example.concordia_campus_guide.Models.Floors;
import com.example.concordia_campus_guide.Models.Rooms;
import com.example.concordia_campus_guide.Models.Shuttle;
import com.example.concordia_campus_guide.R;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;

public class ApplicationState {

    private Context context;
    private static ApplicationState instance;
    private Buildings buildings;
    private Floors floors;
    private Rooms rooms;
    private Shuttle shuttleSchedule;

    private ApplicationState(Context context) {
        this.context = context;
        importBuildings();
        importFloors();
        importRooms();
        importShuttle();
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

    private void importFloors(){
        String json;
        Floors floors = new Floors();

        try{
            InputStream is = context.getResources().openRawResource(R.raw.floors_info);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
            floors = new Gson().fromJson(json, Floors.class);
        } catch (IOException e){
            e.printStackTrace();
        }

        this.floors = floors;
    }

    private void importRooms(){
        String json;
        Rooms rooms = new Rooms();

        try{
            InputStream is = context.getResources().openRawResource(R.raw.rooms_info);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
            rooms = new Gson().fromJson(json, Rooms.class);
        } catch (IOException e){
            e.printStackTrace();
        }

        this.rooms = rooms;
    }

    private void importShuttle() {
        String json;
        Shuttle shuttle = new Shuttle();

        try {
            InputStream is = context.getResources().openRawResource(R.raw.shuttle_info);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
            shuttle = new Gson().fromJson(json, Shuttle.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.shuttleSchedule = shuttle;
    }

    public Buildings getBuildings(){
        return this.buildings;
    }

    public Floors getFloors(){
        return this.floors;
    }

    public Rooms getRooms(){
        return this.rooms;
    }

    public Shuttle getShuttle() {
        return this.shuttleSchedule;
    }

}

package com.example.concordia_campus_guide.Global;

import android.content.Context;

import com.example.concordia_campus_guide.Models.Buildings;
import com.example.concordia_campus_guide.Models.Floors;
import com.example.concordia_campus_guide.Database.AppDatabase;
import com.example.concordia_campus_guide.Models.Rooms;
import com.example.concordia_campus_guide.Models.WalkingPoint;
import com.example.concordia_campus_guide.Models.WalkingPoints;
import com.example.concordia_campus_guide.R;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;

import androidx.room.Room;

public class ApplicationState {

    private Context context;
    private static ApplicationState instance;
    private Buildings buildings;
    private Floors floors;
    private Rooms rooms;
    private WalkingPoints walkingPoints;
    private boolean dbIsSet = false;

    private ApplicationState(Context context) {
        this.context = context;
        importBuildings();
        importFloors();
        importRooms();
        importWalkingPoints();
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

    private void importWalkingPoints(){
        String json;
        WalkingPoints walkingPoints = new WalkingPoints();
        try{
            InputStream is = context.getResources().openRawResource(R.raw.walking_points);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
            walkingPoints = new Gson().fromJson(json, WalkingPoints.class);
        } catch (IOException e){
            e.printStackTrace();
        }
        this.walkingPoints = walkingPoints;
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

    public Buildings getBuildings(){
        return this.buildings;
    }

    public Floors getFloors(){
        return this.floors;
    }

    public Rooms getRooms(){
        return this.rooms;
    }

    public boolean isDbIsSet() {
        return dbIsSet;
    }

    public void setDbIsSetToTrue() {
        this.dbIsSet = true;
    }
    public WalkingPoints getWalkingPoints() { return this.walkingPoints; }

}

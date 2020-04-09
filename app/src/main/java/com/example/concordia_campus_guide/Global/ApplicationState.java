package com.example.concordia_campus_guide.Global;

import android.content.Context;
import android.util.Log;

import com.example.concordia_campus_guide.Models.Buildings;
import com.example.concordia_campus_guide.Models.Floors;
import com.example.concordia_campus_guide.Models.Rooms;
import com.example.concordia_campus_guide.Models.Shuttles;
import com.example.concordia_campus_guide.Models.WalkingPoints;
import com.example.concordia_campus_guide.R;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class ApplicationState {

    private Context context;
    private static ApplicationState instance;
    private Buildings buildings;
    private Floors floors;
    private Rooms rooms;
    private WalkingPoints walkingPoints;
    private boolean dbIsSet = false;
    private Shuttles shuttles;

    private ApplicationState(Context context) {
        this.context = context;
        importBuildings();
        importFloors();
        importRooms();
        importShuttle();
        importWalkingPoints();
    }

    public static ApplicationState getInstance(Context context) {
        if (instance == null) {
            instance = new ApplicationState(context);
        }
        return instance;
    }

    private String importMethod(int jsonFileId) {
        String json = null;
        try {
            InputStream is = context.getResources().openRawResource(jsonFileId);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, StandardCharsets.UTF_8);
        } catch (IOException e) {
            Log.e("ApplicationState", e.getMessage());
        }
        return json;
    }

    private void importBuildings() {
        String json = importMethod(R.raw.buildings_info);
        if (json != null) {
            Buildings fromJson = new Gson().fromJson(json, Buildings.class);
            this.buildings = fromJson;
        }
    }

    private void importWalkingPoints() {
        String json = importMethod(R.raw.walking_points);
        if (json != null) {
            WalkingPoints fromJson = new Gson().fromJson(json, WalkingPoints.class);
            this.walkingPoints = fromJson;
        }
    }

    private void importFloors() {
        String json = importMethod(R.raw.floors_info);
        if (json != null) {
            Floors fromJson = new Gson().fromJson(json, Floors.class);
            this.floors = fromJson;
        }
    }

    private void importRooms() {
        String json = importMethod(R.raw.rooms_info);
        if (json != null) {
            Rooms fromJson = new Gson().fromJson(json, Rooms.class);
            this.rooms = fromJson;
        }
    }

    public Buildings getBuildings() {
        return this.buildings;
    }

    public Floors getFloors() {
        return this.floors;
    }

    public Rooms getRooms() {
        return this.rooms;
    }

    public boolean isDbIsSet() {
        return dbIsSet;
    }

    public void setDbIsSetToTrue() {
        this.dbIsSet = true;
    }

    public WalkingPoints getWalkingPoints() {
        return this.walkingPoints;
    }

    public Shuttles getShuttles() {
        return this.shuttles;
    }

    private void importShuttle() {
        String json = importMethod(R.raw.shuttle_info);
        if (json != null) {
            Shuttles fromJson = new Gson().fromJson(json, Shuttles.class);
            this.shuttles = fromJson;
        }
    }
}

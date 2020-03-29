package com.example.concordia_campus_guide;

import com.example.concordia_campus_guide.Models.WalkingPoint;
import com.example.concordia_campus_guide.Models.WalkingPoints;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class WalkingPointTestUtil {

    public static List<WalkingPoint> walkingPointList;

    private WalkingPointTestUtil(){
        walkingPointList = this.importWalkingPoints();
    }
    public static List<WalkingPoint> getAllWalkingPoints(){
        return walkingPointList;
    }

    public List<WalkingPoint> importWalkingPoints(){
        String json;
        WalkingPoints walkingPoints = new WalkingPoints();
        try{
            InputStream is = getClass().getResourceAsStream("app/src/test/res/test_walking_points.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
            walkingPoints = new Gson().fromJson(json, WalkingPoints.class);
        } catch (IOException e){
            e.printStackTrace();
        }
        return walkingPoints.getWalkingPoints();
    }
}

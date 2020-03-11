package com.example.concordia_campus_guide.Database.Converters;

import android.os.Build;

import com.example.concordia_campus_guide.Models.Coordinates;
import com.example.concordia_campus_guide.Models.WalkingPoint;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import androidx.annotation.RequiresApi;
import androidx.room.TypeConverter;

public class WalkingPointListConverter {
    private static final String SPLIT_CHAR = ",";

    @RequiresApi(api = Build.VERSION_CODES.O)
    @TypeConverter
    public String convertToDatabaseColumn(List<WalkingPoint> connectedWalkingPoints) {
        if(connectedWalkingPoints == null){
            return null;
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<WalkingPoint>>() {}.getType();
        String json = gson.toJson(connectedWalkingPoints, type);
        return json;
    }

    @TypeConverter
    public List<WalkingPoint> convertToEntityAttribute(String connectedWalkingPointsList) {
        if (connectedWalkingPointsList == null){
            return null;
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<WalkingPoint>>() {}.getType();
        List<WalkingPoint> walkingPointList = gson.fromJson(connectedWalkingPointsList, type);
        return walkingPointList;
    }
}
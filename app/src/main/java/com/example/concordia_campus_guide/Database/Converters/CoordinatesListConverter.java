package com.example.concordia_campus_guide.Database.Converters;

import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.room.TypeConverter;

import com.example.concordia_campus_guide.Models.Coordinates;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class CoordinatesListConverter {
    private static final String SPLIT_CHAR = ",";

    @RequiresApi(api = Build.VERSION_CODES.O)
    @TypeConverter
    public String convertToDatabaseColumn(List<Coordinates> listOfCoordinates) {
        if(listOfCoordinates == null){
            return null;
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<Coordinates>>() {}.getType();
        String json = gson.toJson(listOfCoordinates, type);
        return json;
    }

    @TypeConverter
    public List<Coordinates> convertToEntityAttribute(String listOfCoordinatesString) {
        if (listOfCoordinatesString == null){
            return null;
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<Coordinates>>() {}.getType();
        List<Coordinates> listOfCoordinates = gson.fromJson(listOfCoordinatesString, type);
        return listOfCoordinates;
    }
}
package com.example.concordia_campus_guide.database.converters;

import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.room.TypeConverter;

import com.example.concordia_campus_guide.models.Coordinates;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class CoordinatesListConverter {

    @RequiresApi(api = Build.VERSION_CODES.O)
    @TypeConverter
    public String convertToDatabaseColumn(List<Coordinates> listOfCoordinates) {
        if(listOfCoordinates == null){
            return null;
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<Coordinates>>() {}.getType();
        return gson.toJson(listOfCoordinates, type);
    }

    @TypeConverter
    public List<Coordinates> convertToEntityAttribute(String listOfCoordinatesString) {
        if (listOfCoordinatesString == null){
            return new ArrayList<>();
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<Coordinates>>() {}.getType();
        return gson.fromJson(listOfCoordinatesString, type);
    }
}
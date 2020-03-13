package com.example.concordia_campus_guide.Database.Converters;

import com.example.concordia_campus_guide.Models.Coordinates;

import androidx.room.TypeConverter;

public class CoordinatesConverter {
    private static final String SPLIT_CHAR = ",";

    @TypeConverter
    public String convertToDatabaseColumn(Coordinates coordinates) {
        if(coordinates != null){
            return coordinates.getLatitude() + SPLIT_CHAR +coordinates.getLongitude();
        }
        return null;
    }

    @TypeConverter
    public Coordinates convertToEntityAttribute(String stringOfCoordinates) {
        if (stringOfCoordinates != null){
            int index = stringOfCoordinates.indexOf(SPLIT_CHAR);
            double longitude = Double.parseDouble(stringOfCoordinates.substring(0,index));
            double latitude  = Double.parseDouble(stringOfCoordinates.substring(index+1));
            Coordinates  coordinates = new Coordinates(latitude,longitude);
            return coordinates;
        }
        return null;
    }
}

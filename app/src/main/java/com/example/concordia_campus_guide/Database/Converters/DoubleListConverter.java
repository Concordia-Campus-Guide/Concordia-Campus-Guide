package com.example.concordia_campus_guide.Database.Converters;


import androidx.room.TypeConverter;

public class DoubleListConverter {

    private static final String SPLIT_CHAR = ",";

    @TypeConverter
    public String convertToDatabaseColumn(Double[] array) {
        if(array != null ){
            return array[0] + SPLIT_CHAR + array[1];
        }
        return null;
    }

    @TypeConverter
    public Double[] convertToEntityAttribute(String string) {
        if(string != null){
            int index = string.indexOf(SPLIT_CHAR);
            Double[] coordinates = {Double.parseDouble(string.substring(0,index)),
                    Double.parseDouble(string.substring(index+1))};
            return coordinates;
        }
        return null;
    }
}

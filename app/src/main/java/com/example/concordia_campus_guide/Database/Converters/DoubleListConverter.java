package com.example.concordia_campus_guide.Database.Converters;

import android.os.Build;

import java.util.Arrays;
import java.util.List;

import androidx.annotation.RequiresApi;
import androidx.room.TypeConverter;

public class DoubleListConverter {

    private static final String SPLIT_CHAR = ",";

    @TypeConverter
    public String convertToDatabaseColumn(Double[] array) {
        return array[0] + SPLIT_CHAR +array[1];
    }

    @TypeConverter
    public Double[] convertToEntityAttribute(String string) {
        int index = string.indexOf(SPLIT_CHAR);
        Double[] coordinates = {Double.parseDouble(string.substring(0,index)),
                Double.parseDouble(string.substring(index+1))};
        return coordinates;
    }
}

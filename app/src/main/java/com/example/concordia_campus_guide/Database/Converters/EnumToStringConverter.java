package com.example.concordia_campus_guide.Database.Converters;

import com.example.concordia_campus_guide.Models.PointType;

import androidx.room.TypeConverter;

public class EnumToStringConverter {


    @TypeConverter
    public static String fromAccessibilityTypeToString(PointType pointType) {
        if (pointType == null)
            return null;
        return pointType.toString();
    }

    @TypeConverter
    public static PointType fromStringToAccessibilityType(String pointType) {
        if(pointType!= null){
            return PointType.valueOf(pointType);
        }
        return null;
    }
}

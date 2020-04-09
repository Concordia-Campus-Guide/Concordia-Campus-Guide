package com.example.concordia_campus_guide.database.Converters;

import com.example.concordia_campus_guide.models.PointType;

import androidx.room.TypeConverter;

public class EnumToStringConverter {

    @TypeConverter
    public String fromPointTypeToString(PointType pointType) {
        if (pointType == null)
            return null;
        return pointType.toString();
    }

    @TypeConverter
    public PointType fromStringToPointType(String pointType) {
        if(pointType!= null){
            return PointType.valueOf(pointType);
        }
        return null;
    }
}

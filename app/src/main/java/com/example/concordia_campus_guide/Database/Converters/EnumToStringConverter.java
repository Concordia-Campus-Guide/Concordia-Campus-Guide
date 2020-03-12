package com.example.concordia_campus_guide.Database.Converters;

import com.example.concordia_campus_guide.Models.AccessibilityType;

import androidx.room.TypeConverter;

public class EnumToStringConverter {


    @TypeConverter
    public static String fromAccessibilityTypeToString(AccessibilityType accessibilityType) {
        if (accessibilityType == null)
            return null;
        return accessibilityType.toString();
    }

    @TypeConverter
    public static AccessibilityType fromStringToAccessibilityType (String accessibilityType) {
        if(accessibilityType!= null){
            return AccessibilityType.valueOf(accessibilityType);
        }
        return null;
    }
}

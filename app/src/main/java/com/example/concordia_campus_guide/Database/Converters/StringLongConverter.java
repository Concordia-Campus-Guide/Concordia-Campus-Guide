package com.example.concordia_campus_guide.Database.Converters;

import androidx.room.TypeConverter;

public class StringLongConverter {
    @TypeConverter
    public long convertToDatabaseColumn(String time) {
        if (time != null) {
            return Long.parseLong(time.replace(":", "."));
        }
        return 0;
    }

    @TypeConverter
    public String convertToEntityAttribute(long time) {
        if (time != 0){
            return Long.toString(time).replace(".", ":");
        }
        return null;
    }
}

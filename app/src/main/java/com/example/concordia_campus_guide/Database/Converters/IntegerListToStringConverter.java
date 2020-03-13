package com.example.concordia_campus_guide.Database.Converters;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import androidx.room.TypeConverter;
public class IntegerListToStringConverter {
    private static final String SPLIT_CHAR = ",";

    @TypeConverter
    public String convertToDatabaseColumn(List<Integer> listOfIds) {
        if(listOfIds != null ){
            StringBuilder idsInString = new StringBuilder();
            for (int id : listOfIds ){
                idsInString.append(id);
            }
            return  idsInString.toString();
        }
        return null;
    }

    @TypeConverter
    public List<Integer> convertToEntityAttribute(String string) {
        if(string != null){
            List<String> listOfIdsString = Arrays.asList(string.split(SPLIT_CHAR));
            List<Integer> listOfIds = new ArrayList<>();
            for (String id : listOfIdsString) {
                listOfIds.add(Integer.parseInt(id));
            }
            return listOfIds;
        }
        return null;
    }
}

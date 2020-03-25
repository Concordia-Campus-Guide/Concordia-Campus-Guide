package com.example.concordia_campus_guide.ConvertersTest;

import com.example.concordia_campus_guide.Database.Converters.StringListConverter;
import com.example.concordia_campus_guide.Models.Building;
import com.example.concordia_campus_guide.Models.Coordinates;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNull;

public class StringListConverterTest {

   private Building building;
   private StringListConverter stringListConverter;


    @Before
    public void init() {
        building  = new Building(new Coordinates(45.4972685, -73.5789475), new ArrayList<String>(Arrays.asList("8","9")), 68, 68, 34, null, "H", null, null, null, null, null);
        stringListConverter = new StringListConverter();
    }

    @Test
    public void convertListToStringTest(){
        List <String> departments = new ArrayList<>();
        departments.add("Computer Science & Software Engineering");
        departments.add("Art");
        departments.add("Business");
        building.setDepartments(departments);
        String strResult = stringListConverter.convertToDatabaseColumn(building.getDepartments());
        assertEquals("convertListToStringTest: ", "Computer Science & Software Engineering,Art,Business",strResult);
    }

    @Test
    public void convertListToStringNullTest() {
        assertNull("convertListToStringTest: ", stringListConverter.convertToDatabaseColumn(null));
    }

    @Test
    public void convertStringToListTest(){
        String str  = "Welcome Crew Office,Centre for Teaching & Learning,Loyola International College";
        List <String> services = new ArrayList<>();
        services.add("Welcome Crew Office");
        services.add("Centre for Teaching & Learning");
        services.add("Loyola International College");
        building.setServices(services);
        List <String> listOfServices = stringListConverter.convertToEntityAttribute(str);

        for(int i =0; i <listOfServices.size(); i++){
            assertEquals("convertStringToList: ",building.getServices().get(i), listOfServices.get(i) );
        }
    }

    @Test
    public void convertStringToListNullTest() {
        assertNull("convertListToStringTest: ",stringListConverter.convertToEntityAttribute(null));
    }
}

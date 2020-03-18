package com.example.concordia_campus_guide.ConvertersTest;

import com.example.concordia_campus_guide.Database.Converters.CoordinatesConverter;
import com.example.concordia_campus_guide.Database.Converters.CoordinatesListConverter;
import com.example.concordia_campus_guide.Models.Building;
import com.example.concordia_campus_guide.Models.Coordinates;

import org.junit.Before;
import org.junit.Test;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static junit.framework.TestCase.assertEquals;

public class CoordinatesConverterTest {
    private CoordinatesConverter coordinatesConverter;

    @Before
    public void init() {
        coordinatesConverter = new CoordinatesConverter();
    }

    @Test
    public void convertCoordinatesToStringTest(){
        Coordinates coordinates = new Coordinates(-73.57883681, 45.49731974);
        String coordinateString = coordinatesConverter.convertToDatabaseColumn(coordinates);
        assertEquals("convertCoordinatesToStringTest: ", "-73.57883681,45.49731974", coordinateString);
    }

    @Test
    public void convertStringToCoordinatesTest(){
        String coordinatesString = "-73.57883681,45.49731974";
        Coordinates coordinates  = coordinatesConverter.convertToEntityAttribute(coordinatesString);
        assertEquals("convertCoordinatesToStringTest Latitude: ", -73.57883681, coordinates.getLongitude());
        assertEquals("convertCoordinatesToStringTest Latitude: ",  45.49731974,  coordinates.getLatitude());
    }



}

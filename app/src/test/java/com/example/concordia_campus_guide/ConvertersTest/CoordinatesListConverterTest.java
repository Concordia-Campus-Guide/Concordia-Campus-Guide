package com.example.concordia_campus_guide.ConvertersTest;

import com.example.concordia_campus_guide.Database.Converters.CoordinatesListConverter;
import com.example.concordia_campus_guide.Models.Coordinates;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertNull;
import static junit.framework.TestCase.assertTrue;

public class CoordinatesListConverterTest {

    private CoordinatesListConverter coordinatesListConverter;
    private List<Coordinates> coordinatesList;
    private String json;

    @Before
    public void init() {
        coordinatesList = new ArrayList<>();
        coordinatesListConverter = new CoordinatesListConverter();
        setupCoordinatesList();
        setupJsonString();
    }

    private void setupCoordinatesList() {
        coordinatesList.add(new Coordinates(-73.57883681, 45.49731974));
        coordinatesList.add(new Coordinates( 45.49731974, -73.57883681));
        coordinatesList.add(new Coordinates( 0, 0));
    }

    private void setupJsonString(){
        json = "[{\"latitude\":-73.57883681,\"longitude\":45.49731974},"+
                "{\"latitude\":45.49731974,\"longitude\":-73.57883681},"+
                "{\"latitude\":0.0,\"longitude\":0.0}]";
    }

    @Test
    public void convertListOfCoordinatesToStringTest(){
        String jsonObject = coordinatesListConverter.convertToDatabaseColumn(coordinatesList);
        assertEquals("convertListOfCoordinatesToStringTest:", json,jsonObject);
    }

    @Test
    public void convertListOfCoordinatesToStringNullTest(){
        assertNull(coordinatesListConverter.convertToDatabaseColumn(null));
    }

    @Test
    public void convertStringToListOfCoordinatesNullTest(){
        assertNull(coordinatesListConverter.convertToDatabaseColumn(null));
    }

    @Test
    public void convertStringToListOfCoordinatesTest(){
        List<Coordinates> listOfCoordinatesTemp = coordinatesListConverter.convertToEntityAttribute(json);
        for (int i =0; i <listOfCoordinatesTemp.size(); i++){
            assertTrue("convertStringToListOfCoordinatesTest: ",listOfCoordinatesTemp.get(i).equals(coordinatesList.get(i)));
        }
    }
}

package com.example.concordia_campus_guide.ConvertersTest;

import com.example.concordia_campus_guide.Database.Converters.CoordinatesListConverter;
import com.example.concordia_campus_guide.Database.Converters.IntegerListToStringConverter;
import com.example.concordia_campus_guide.Models.Coordinates;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNull;
import static junit.framework.TestCase.assertTrue;

public class IntegerListToStringConverterTest{
    private IntegerListToStringConverter integerListToStringConverter;
    private List<Integer> listOfIntegers;

    @Before
    public void init() {
        listOfIntegers = new ArrayList<>();
        integerListToStringConverter = new IntegerListToStringConverter();
        setupIntegersList();
    }

    private void setupIntegersList() {
        listOfIntegers.add(1);
        listOfIntegers.add(2);
        listOfIntegers.add(3);
    }


    @Test
    public void convertListOfIntegerToStringTest(){
        String stringOfIds = integerListToStringConverter.convertToDatabaseColumn(listOfIntegers);
        assertEquals("convertListOfIntegerToStringTest: ", "1,2,3,",stringOfIds);
    }
    @Test
    public void convertListOfIntegerToStringNullTest(){
        assertNull("convertListOfIntegerToStringTest: ", integerListToStringConverter.convertToDatabaseColumn(null));
    }

    @Test
    public void convertStringToListOfInteger(){
        String str = "1,2,3,";
        List<Integer> listOfIntegerTemp = integerListToStringConverter.convertToEntityAttribute(str);
        for (int i =0; i <listOfIntegerTemp.size(); i++){
            assertTrue("convertStringToListOfInteger: ",listOfIntegers.get(i).equals(listOfIntegerTemp.get(i)));
        }
    }

    @Test
    public void convertStringToListOfIntegerNullTest(){
        assertNull("convertStringToListOfInteger: ", integerListToStringConverter.convertToEntityAttribute(null));
    }
}

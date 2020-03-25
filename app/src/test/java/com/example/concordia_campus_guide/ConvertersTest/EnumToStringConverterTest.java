package com.example.concordia_campus_guide.ConvertersTest;

import com.example.concordia_campus_guide.Database.Converters.EnumToStringConverter;
import com.example.concordia_campus_guide.Models.PointType;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNull;

public class EnumToStringConverterTest {
    private EnumToStringConverter enumToStringConverter;

    @Before
    public void init() {
        enumToStringConverter = new EnumToStringConverter();
    }

    @Test
    public void fromPointTypeToStringTest(){
        String str = enumToStringConverter.fromPointTypeToString(PointType.ELEVATOR);
        assertEquals("fromPointTypeToStringTest: ", "ELEVATOR",str );
    }

    @Test
    public void fromPointTypeToStringNullTest(){
        assertNull("fromPointTypeToStringTest: ", enumToStringConverter.fromPointTypeToString(null) );
    }

    @Test
    public void fromStringToPointTypeTest(){
        PointType pointType = enumToStringConverter.fromStringToPointType("ELEVATOR");
        assertEquals("fromStringToPointTypeTest: ", PointType.ELEVATOR, pointType);
    }
    @Test
    public void fromStringToPointTypeNullTest(){
        assertNull("fromStringToPointTypeTest: ",  enumToStringConverter.fromPointTypeToString(null) );
    }
}

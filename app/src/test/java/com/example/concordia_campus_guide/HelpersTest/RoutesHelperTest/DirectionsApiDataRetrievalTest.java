package com.example.concordia_campus_guide.HelpersTest.RoutesHelperTest;

import com.example.concordia_campus_guide.activities.RoutesActivity;
import com.example.concordia_campus_guide.BuildConfig;
import com.example.concordia_campus_guide.helper.routes_helpers.DirectionsApiDataRetrieval;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static junit.framework.TestCase.assertNull;
import static org.junit.Assert.assertNotNull;

public class DirectionsApiDataRetrievalTest {

    private DirectionsApiDataRetrieval directionsApiDataRetrieval;

    @Mock
    RoutesActivity routesActivity;

    @Before
    public void setUp() {
        directionsApiDataRetrieval = new DirectionsApiDataRetrieval(routesActivity);
    }

    @Test
    public void getDataFromGMapsDirectionsApi_CorrectUrlFormatTest() {
        // Arrange
        String url = "https://maps.googleapis.com/maps/api/directions/" + "json" + "?" + "origin=45.525407,-73.677126&destination=45.497361,-73.579033&mode=driving&alternatives=true"
                + "&key=" + BuildConfig.API_KEY;
        Object returnedObject = null;

        // Act
        try {
            // Accessing and testing a private method
            Method method = directionsApiDataRetrieval.getClass().getDeclaredMethod("getDataFromGMapsDirectionsApi", String.class);
            method.setAccessible(true);
            returnedObject = method.invoke(directionsApiDataRetrieval, url);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        // Assert
        assertNotNull(returnedObject);
    }

    @Test
    public void getDataFromGMapsDirectionsApi_IncorrectUrlFormatTest() {
        // Arrange
        String url = "";
        Object returnedObject = null;

        // Act
        try {
            // Accessing and testing a private method
            Method method = directionsApiDataRetrieval.getClass().getDeclaredMethod("getDataFromGMapsDirectionsApi", String.class);
            method.setAccessible(true);
            returnedObject = method.invoke(directionsApiDataRetrieval, url);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        // Assert
        assertNull(returnedObject);
    }
}

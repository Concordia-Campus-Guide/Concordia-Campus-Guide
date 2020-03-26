package com.example.concordia_campus_guide.HelpersTest.RoutesHelperTest;

import com.example.concordia_campus_guide.BuildConfig;
import com.example.concordia_campus_guide.ClassConstants;
import com.example.concordia_campus_guide.Helper.RoutesHelpers.UrlBuilder;
import com.google.android.gms.maps.model.LatLng;

import org.junit.Assert;
import org.junit.Test;

public class UrlBuilderTest {

    @Test
    public void buildTest() {
        // Arrange
        String expectedUrl = "https://maps.googleapis.com/maps/api/directions/" + "json" + "?" + "origin=45.525407,-73.677126&destination=45.497361,-73.579033&mode=driving&alternatives=true"
                + "&key=" + BuildConfig.API_KEY;

        // Act
        String actualUrl = UrlBuilder.build(new LatLng(45.525407, -73.677126), new LatLng(45.497361, -73.579033), ClassConstants.DRIVING);

        // Assert
        Assert.assertEquals(UrlBuilder.class.toString(), expectedUrl, actualUrl);

    }
}

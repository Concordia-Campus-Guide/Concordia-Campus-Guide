package com.example.concordia_campus_guide;

import android.graphics.Color;

import com.example.concordia_campus_guide.LocationFragment.LocationFragmentViewModel;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.maps.android.geojson.GeoJsonLayer;
import com.google.maps.android.geojson.GeoJsonPolygonStyle;

import org.junit.Test;
import org.junit.Before;
import org.mockito.MockitoAnnotations;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;


/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class LocationFragmentViewModelTest {
    private LocationFragmentViewModel viewModel;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        viewModel = new LocationFragmentViewModel();
    }


    @Test
    public void getPolygonStyleTest(){
        GeoJsonPolygonStyle  geoJsonPolygonStyle = viewModel.getPolygonStyle();
        assertEquals(geoJsonPolygonStyle.getFillColor(), Color.argb(51, 18, 125, 159));
        assertEquals(Color.argb(255, 18, 125, 159), geoJsonPolygonStyle.getStrokeColor());
        assertEquals(6.0f, geoJsonPolygonStyle.getStrokeWidth());
    }

    @Test
    public void getMapStyle(){
        assertEquals(viewModel.getMapStyle(), R.raw.mapstyle_retro);
    }
}

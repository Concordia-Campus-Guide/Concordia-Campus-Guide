package com.example.concordia_campus_guide;

import android.graphics.Color;

import com.example.concordia_campus_guide.Fragments.LocationFragment.LocationFragmentViewModel;
import com.example.concordia_campus_guide.Models.Building;
import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.geojson.GeoJsonPolygonStyle;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import static junit.framework.TestCase.assertEquals;


/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class LocationFragmentViewModelTest  {
    private LocationFragmentViewModel viewModel;
    private HashMap<String, Building> buildings;
    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        viewModel = new LocationFragmentViewModel();
        buildings = new HashMap<>();
        setupBuildings();
    }

    private void setupBuildings() {
        buildings.put("H", new Building(new Double[]{45.4972685, -73.5789475}, new ArrayList<String>(Arrays.asList("8","9")), 68, 68, 34, null, "H", null, null, null, null, null));
        buildings.put("EV", new Building(new Double[]{45.495638, -73.578258}, new ArrayList<String>(Arrays.asList("8","9")), 68, 68, 34, null, "H", null, null, null, null, null));
        buildings.put("VL", new Building(new Double[]{45.45909, -73.63844}, new ArrayList<String>(Arrays.asList("8","9")), 80, 45, 210, null, "VL", null, null, null, null, null));
        viewModel.setBuildings(buildings);
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
    @Test
    public void getBuildingsTest(){
        HashMap<String, Building> temp = new HashMap<>();
        temp.put("H", new Building(new Double[]{45.4972685, -73.5789475}, new ArrayList<String>(Arrays.asList("8","9")), 68, 68, 34, null, "H", null, null, null, null, null));
        viewModel.setBuildings(temp);
        assertEquals(temp, viewModel.getBuildings());
        viewModel.setBuildings(buildings);
        assertEquals(buildings, viewModel.getBuildings());
    }
    @Test
    public void getInitialZoomLocationTest(){
        assertEquals(new LatLng( 45.495638, -73.578258), viewModel.getInitialZoomLocation());
    }
    @Test
    public void getSGWZoomLocationTest(){
        assertEquals(new LatLng( 45.4972685, -73.5789475), viewModel.getSGWZoomLocation());
    }
    @Test
    public void getLoyolaZoomLocationTest(){
        assertEquals(new LatLng( 45.45909, -73.63844), viewModel.getLoyolaZoomLocation());
    }
    @Test
    public void getBuildingFromeCodeTest(){
        assertEquals(buildings.get("EV"), viewModel.getBuildingFromeCode("EV"));
    }

}

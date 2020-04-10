package com.example.concordia_campus_guide;

import android.graphics.Color;

import com.example.concordia_campus_guide.database.AppDatabase;
import com.example.concordia_campus_guide.database.daos.WalkingPointDao;
import com.example.concordia_campus_guide.helper.BuildingCodeMap;
import com.example.concordia_campus_guide.helper.DrawPolygons;
import com.example.concordia_campus_guide.models.Building;
import com.example.concordia_campus_guide.models.Coordinates;
import com.example.concordia_campus_guide.models.WalkingPoint;
import com.example.concordia_campus_guide.view_models.LocationFragmentViewModel;
import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.geojson.GeoJsonPolygonStyle;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import static junit.framework.TestCase.assertEquals;
import static org.mockito.Mockito.when;


/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class LocationFragmentViewModelTest  {

    @Mock
    AppDatabase mockAppDb;
    LocationFragmentViewModel viewModel;

    @Mock
    WalkingPointDao mockWalkingPointDao;

    private BuildingCodeMap buildings;
    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        when(mockAppDb.walkingPointDao()).thenReturn(mockWalkingPointDao);

        //TODO: Create test data to test the POI generation
        when(mockWalkingPointDao.getAllWalkingPointsFromPlaceCode(Mockito.anyString())).thenReturn(new ArrayList<WalkingPoint>());
        viewModel = new LocationFragmentViewModel(mockAppDb);
        buildings = new BuildingCodeMap();
        setupBuildings();
    }

    private void setupBuildings() {
        buildings.getBuildings().put("EV", new Building(new Coordinates(45.495638, -73.578258), new ArrayList<String>(Arrays.asList("8","9")), 68, 68, 34, null, "H", null, null, null, null, null));
        buildings.getBuildings().put("CC", new Building(new Coordinates(45.45821918855051, -73.64035427570343), new ArrayList<String>(Arrays.asList("8","9")), 80, 45, 210, null, "VL", null, null, null, null, null));
        viewModel.setBuildings(buildings.getBuildings());
    }

    @Test
    public void getPolygonStyleTest(){
        DrawPolygons drawPolygons = new DrawPolygons();
        GeoJsonPolygonStyle  geoJsonPolygonStyle = drawPolygons.getPolygonStyle();
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
        temp.put("H", new Building(new Coordinates(45.4972685, -73.5789475), new ArrayList<String>(Arrays.asList("8","9")), 68, 68, 34, null, "H", null, null, null, null, null));
        viewModel.setBuildings(temp);
        assertEquals(temp, viewModel.getBuildings());
        viewModel.setBuildings(buildings.getBuildings());
        assertEquals(buildings.getBuildings(), viewModel.getBuildings());
    }
    @Test
    public void getInitialZoomLocationTest(){
        assertEquals(new LatLng( 45.495638, -73.578258), viewModel.getInitialZoomLocation());
    }
    @Test
    public void getSGWZoomLocationTest(){
        assertEquals(new LatLng( 45.495638, -73.578258), viewModel.getZoomLocation(ClassConstants.SGW_CENTER_BUILDING_LABEL));
    }
    @Test
    public void getLoyolaZoomLocationTest(){
        assertEquals(new LatLng( 45.45821918855051, -73.64035427570343), viewModel.getZoomLocation(ClassConstants.LOYOLA_CENTER_BUILDING_LABEL));
    }
    @Test
    public void getBuildingFromCodeTest(){
        assertEquals(buildings.getBuildingFromCode("EV"), viewModel.getBuildingFromCode("EV"));
    }

}

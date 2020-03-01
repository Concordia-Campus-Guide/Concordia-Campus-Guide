package com.example.concordia_campus_guide;

import android.graphics.Color;

import com.example.concordia_campus_guide.Activities.MainActivity;
import com.example.concordia_campus_guide.LocationFragment.LocationFragmentViewModel;
import com.google.maps.android.geojson.GeoJsonPolygonStyle;
import org.junit.Test;
import org.junit.Before;
import org.mockito.MockitoAnnotations;
import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;


/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class LocationFragmentViewModelTest  {
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
  
    @Test
    public void getBuildingFromGeoJsonFeatureTest(){

    }
 /**
  *     public Building getBuildingFromGeoJsonFeature(GeoJsonFeature feature){
  *         LatLng centerPos = getCenterPositionBuildingFromGeoJsonFeature(feature);
  *         String[] floorsAvailable = feature.getProperty("floorsAvailable").split(",");
  *         float building_width = Float.parseFloat(feature.getProperty("width"));
  *         float building_height = Float.parseFloat(feature.getProperty("height"));
  *         String building_code = feature.getProperty("code").toLowerCase();
  *
  *         return new Building(centerPos,floorsAvailable, building_code, building_width, building_height);
  *     }
 */
}

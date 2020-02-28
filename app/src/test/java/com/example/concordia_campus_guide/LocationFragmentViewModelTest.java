package com.example.concordia_campus_guide;

import android.graphics.Color;

import com.example.concordia_campus_guide.LocationFragment.LocationFragmentViewModel;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.GroundOverlayOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.geojson.GeoJsonPolygonStyle;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.Before;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;

import static junit.framework.TestCase.assertEquals;


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
    public void getIconTest(){
        assertEquals(R.drawable.h, viewModel.getIcon(BuildingCode.H));
        assertEquals(R.drawable.ad,viewModel.getIcon(BuildingCode.AD));
        assertEquals(R.drawable.cc, viewModel.getIcon(BuildingCode.CC));
        assertEquals(R.drawable.cj, viewModel.getIcon(BuildingCode.CJ));
        assertEquals(R.drawable.ev, viewModel.getIcon(BuildingCode.EV));
        assertEquals(R.drawable.fb, viewModel.getIcon(BuildingCode.FB));
        assertEquals(R.drawable.fg, viewModel.getIcon(BuildingCode.FG));
        assertEquals(R.drawable.gm, viewModel.getIcon(BuildingCode.GM));
        assertEquals(R.drawable.lb, viewModel.getIcon(BuildingCode.LB));
        assertEquals(R.drawable.mb, viewModel.getIcon(BuildingCode.MB));
        assertEquals(R.drawable.sp, viewModel.getIcon(BuildingCode.SP));
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
    public void getFloorPlansHBuildingTest(){
        ArrayList<String> expectedFloorPlansAvailable = new ArrayList<>();
        expectedFloorPlansAvailable.add("hall_9");
        expectedFloorPlansAvailable.add("hall_8");

        assertEquals(expectedFloorPlansAvailable, viewModel.getFloorsAvailable(BuildingCode.H));
    }

    @Test
    public void getFloorPlansMBBuildingTest(){
        ArrayList<String> expectedFloorPlansAvailable = new ArrayList<>();
        expectedFloorPlansAvailable.add("mb_1");
        expectedFloorPlansAvailable.add("mb_S2");

        assertEquals(expectedFloorPlansAvailable, viewModel.getFloorsAvailable(BuildingCode.MB));
    }
    @Test
    public void getFloorPlansVLBuildingTest(){
        ArrayList<String> expectedFloorPlansAvailable = new ArrayList<>();
        expectedFloorPlansAvailable.add("vl_2");
        expectedFloorPlansAvailable.add("vl_1");

        assertEquals(expectedFloorPlansAvailable, viewModel.getFloorsAvailable(BuildingCode.VL));
    }
    @Test
    public void getFloorPlansNotSupportedBuildingTest(){
        ArrayList<String> expectedFloorPlansAvailable = new ArrayList<>();

        assertEquals(expectedFloorPlansAvailable, viewModel.getFloorsAvailable(BuildingCode.EV));
    }

    @Ignore
    @Test
    public void getHallBuildingOverlayTest(){
        GroundOverlayOptions actualGOO = viewModel.getHallBuildingOverlay();
        GroundOverlayOptions expectedGOO = new GroundOverlayOptions().position(new LatLng(45.4972685, -73.5789475), (float) 68, (float) 68).image(BitmapDescriptorFactory.fromResource(R.drawable.hall_9)).bearing((float) 34);

        assertEquals(expectedGOO.getLocation(), actualGOO.getLocation());
        assertEquals(expectedGOO.getImage(), actualGOO.getImage());
        assertEquals(expectedGOO.getHeight(), actualGOO.getHeight());
        assertEquals(expectedGOO.getWidth(), actualGOO.getWidth());
        assertEquals(expectedGOO.getBearing(), actualGOO.getBearing());
    }
}

package com.example.concordia_campus_guide;

import com.example.concordia_campus_guide.LocationFragment.LocationFragmentViewModel;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolygonOptions;

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
    public void getPolygonTest(){
        PolygonOptions polygonOptions = new PolygonOptions()
                .clickable(true)
                .add(
                        new LatLng(45.497178, -73.579550),
                        new LatLng(45.497708, -73.579035),
                        new LatLng(45.497385, -73.578332),
                        new LatLng(45.496832, -73.578842),
                        new LatLng(45.497178, -73.579550));
        assertEquals(polygonOptions.getPoints(), viewModel.getPolygon().getPoints());
        assertEquals(polygonOptions.getFillColor(), viewModel.getPolygon().getFillColor());
        assertEquals(polygonOptions.getHoles(), viewModel.getPolygon().getHoles());
        assertEquals(polygonOptions.getStrokeColor(), viewModel.getPolygon().getStrokeColor());
    }

    @Test
    public void getMapStyle(){
        assertEquals(viewModel.getMapStyle(), R.raw.mapstyle_retro);
    }
}

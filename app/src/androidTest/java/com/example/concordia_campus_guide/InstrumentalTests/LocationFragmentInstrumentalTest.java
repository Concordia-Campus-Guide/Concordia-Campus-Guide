package com.example.concordia_campus_guide.InstrumentalTests;

import android.content.Context;
import android.graphics.Bitmap;

import androidx.test.filters.SmallTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.rule.GrantPermissionRule;
import androidx.test.runner.AndroidJUnit4;

import com.example.concordia_campus_guide.activities.MainActivity;
import com.example.concordia_campus_guide.database.AppDatabase;
import com.example.concordia_campus_guide.view_models.LocationFragmentViewModel;
import com.example.concordia_campus_guide.models.Coordinates;
import com.example.concordia_campus_guide.models.RoomModel;
import com.google.android.gms.maps.model.PolylineOptions;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertTrue;

@RunWith(AndroidJUnit4.class)
@SmallTest
public class LocationFragmentInstrumentalTest {
    private LocationFragmentViewModel viewModel;
    private Context appContext;
    private AppDatabase appDatabase;

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(MainActivity.class);

    @Rule
    public GrantPermissionRule mGrantPermissionRule =
            GrantPermissionRule.grant(
                    "android.permission.ACCESS_FINE_LOCATION",
                    "android.permission.ACCESS_COARSE_LOCATION",
                    "android.permission.READ_CALENDAR");

    @Before
    public void init() {
        appContext = mActivityRule.getActivity().getApplicationContext();
        appDatabase = AppDatabase.getInstance(appContext);
        viewModel = new LocationFragmentViewModel(appDatabase);
    }

    @Test
    public void createMarkerIcon(){
        Bitmap bitmap1 = viewModel.createBitmapMarkerIcon("EV");
        Bitmap bitmap2 = viewModel.createBitmapMarkerIcon("EV");
        Bitmap bitmap3 = viewModel.createBitmapMarkerIcon("H");
        assertNotNull(bitmap1);
        assertNotNull(bitmap2);
        assertNotNull(bitmap3);
        assertTrue(bitmap1.sameAs(bitmap2));
        assertFalse(bitmap1.sameAs(bitmap3));
    }

    @Test
    public void getFloorPolylinesTest(){
        RoomModel roomFrom = new RoomModel(new Coordinates(-73.57907921075821, 45.49702057370776), "823", "H-8", "SGW");
        RoomModel roomTo = new RoomModel(new Coordinates(-73.57902321964502, 45.49699848270905), "927", "H-9", "SGW");
        viewModel.parseWalkingPointList(appDatabase,roomFrom,roomTo);
        PolylineOptions polylineOptions = viewModel.getFloorPolylines("H-9");
        assertEquals(10.0f,polylineOptions.getWidth());
        assertTrue(polylineOptions.isVisible());
        assertEquals("[Gap: length=20.0]",polylineOptions.getPattern().get(0).toString());
        assertEquals(-7134407,polylineOptions.getColor());
    }

    @Test
    public void stylePolyLineTest(){
        PolylineOptions polylineOptions = viewModel.stylePolyLine("walking", 0);
        assertEquals("[Gap: length=20.0]",polylineOptions.getPattern().get(0).toString());
        assertEquals(-7134407,polylineOptions.getColor());
    }





}


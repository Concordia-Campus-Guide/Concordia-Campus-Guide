package com.example.concordia_campus_guide.HelpersTest;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;

import com.example.concordia_campus_guide.Helper.PathFinder;
import com.example.concordia_campus_guide.Models.Coordinates;
import com.example.concordia_campus_guide.Models.PointType;
import com.example.concordia_campus_guide.Models.RoomModel;
import com.example.concordia_campus_guide.Models.WalkingPoint;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

public class PathFinderTest {

    @Mock
    private Context mockApplicationContext;
    @Mock
    private Resources mockContextResources;
    @Mock
    private SharedPreferences mockSharedPreferences;

    private PathFinder pathFinder;
    private RoomModel roomSource;
    private RoomModel roomDestination;
    @Before
    public void setupTests() {
        // Mockito has a very convenient way to inject mocks by using the @Mock annotation. To
        // inject the mocks in the test the initMocks method needs to be called.
        MockitoAnnotations.initMocks(this);

        // During unit testing sometimes test fails because of your methods
        // are using the app Context to retrieve resources, but during unit test the Context is null
        // so we can mock it.

        when(mockApplicationContext.getResources()).thenReturn(mockContextResources);
        when(mockApplicationContext.getSharedPreferences(anyString(), anyInt())).thenReturn(mockSharedPreferences);
        when(mockApplicationContext.getApplicationContext()).thenReturn(mockApplicationContext);

        when(mockContextResources.getString(anyInt())).thenReturn("mocked string");
        when(mockContextResources.getStringArray(anyInt())).thenReturn(new String[]{"mocked string 1", "mocked string 2"});
        when(mockContextResources.getColor(anyInt())).thenReturn(Color.BLACK);
        when(mockContextResources.getBoolean(anyInt())).thenReturn(false);
        when(mockContextResources.getDimension(anyInt())).thenReturn(100f);
        when(mockContextResources.getIntArray(anyInt())).thenReturn(new int[]{1,2,3});

        roomSource = new RoomModel(new Double[]{-73.57907921075821, 45.49702057370776}, "823", "H-8");
        roomDestination  = new RoomModel(new Double[]{-73.57902321964502, 45.49699848270905}, "821", "H-8");

        pathFinder = new PathFinder(mockApplicationContext, roomSource, roomDestination);
    }

    @Test
    public void getWalkingPointCorrespondingToRoomTest(){
        List<WalkingPoint> walkingPoints = new ArrayList<>();
        WalkingPoint walkingPointTest = new WalkingPoint(new Coordinates(-73.57907921075821, 45.49702057370776), "H-8", null, PointType.CLASSROOM);
        walkingPoints.add(walkingPointTest);
        assertEquals(pathFinder.getWalkingPointCorrespondingToRoom(roomSource, walkingPoints), walkingPointTest);
    }

    @Test
    public void isGoal()
    /**
     * protected boolean isGoal(final WalkingPoint currentLocation) {
     *         return destinationPoint.equals(currentLocation);
     *     }
     */

}

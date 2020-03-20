package com.example.concordia_campus_guide.HelpersTest;

import com.example.concordia_campus_guide.Database.AppDatabase;
import com.example.concordia_campus_guide.Database.Daos.WalkingPointDao;
import com.example.concordia_campus_guide.Helper.IndoorPathHeuristic;
import com.example.concordia_campus_guide.Models.Coordinates;
import com.example.concordia_campus_guide.Models.PointType;
import com.example.concordia_campus_guide.Models.WalkingPoint;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static org.mockito.Mockito.when;
@RunWith(MockitoJUnitRunner.class)
//public class PathFinderTest {
//
//
//    @Mock
//    AppDatabase mockAppDb;
//
//
//    @Mock
//    WalkingPointDao mockWalkingPointDao;
//
//    @Mock
//    private Resources mockContextResources;
//
//    @Mock
//    private Context mockApplicationContext;
//
//    @Mock
//    private SharedPreferences mockSharedPreferences;
//
//    @Mock
//    WalkingPointDao mockRoomDao;
//
//    private PathFinder pathFinder;
//    private RoomModel roomSource;
//    private RoomModel roomDestination;
//    private Context context;
//    //    @Before
////    public void setupTests() {
////        // Mockito has a very convenient way to inject mocks by using the @Mock annotation. To
////        // inject the mocks in the test the initMocks method needs to be called.
////        MockitoAnnotations.initMocks(this);
////        // During unit testing sometimes test fails because of your methods
////        // are using the app Context to retrieve resources, but during unit test the Context is null
////        // so we can mock it.
////
////        when(mockApplicationContext.getResources()).thenReturn(mockContextResources);
////        when(mockApplicationContext.getSharedPreferences(anyString(), anyInt())).thenReturn(mockSharedPreferences);
////        when(mockApplicationContext.getApplicationContext()).thenReturn(mockApplicationContext);
////
////        when(mockContextResources.getString(anyInt())).thenReturn("mocked string");
////        when(mockContextResources.getStringArray(anyInt())).thenReturn(new String[]{"mocked string 1", "mocked string 2"});
////        when(mockContextResources.getColor(anyInt())).thenReturn(Color.BLACK);
////        when(mockContextResources.getBoolean(anyInt())).thenReturn(false);
////        when(mockContextResources.getDimension(anyInt())).thenReturn(100f);
////        when(mockContextResources.getIntArray(anyInt())).thenReturn(new int[]{1,2,3});
////
////        roomSource = new RoomModel(new Double[]{-73.57907921075821, 45.49702057370776}, "823", "H-8");
////        roomDestination  = new RoomModel(new Double[]{-73.57902321964502, 45.49699848270905}, "821", "H-8");
////        setUpDb();
////        pathFinder = new PathFinder(mockApplicationContext, roomSource, roomDestination);
////    }
//    @Before
//    public void setUp() {
//        when(mockApplicationContext.getResources()).thenReturn(mockContextResources);
//        when(mockApplicationContext.getSharedPreferences(anyString(), anyInt())).thenReturn(mockSharedPreferences);
//        when(mockApplicationContext.getApplicationContext()).thenReturn(mockApplicationContext);
//
//        when(mockContextResources.getString(anyInt())).thenReturn("mocked string");
//        when(mockContextResources.getStringArray(anyInt())).thenReturn(new String[]{"mocked string 1", "mocked string 2"});
//        when(mockContextResources.getColor(anyInt())).thenReturn(Color.BLACK);
//        when(mockContextResources.getBoolean(anyInt())).thenReturn(false);
//        when(mockContextResources.getDimension(anyInt())).thenReturn(100f);
//        when(mockContextResources.getIntArray(anyInt())).thenReturn(new int[]{1,2,3});
//        MockitoAnnotations.initMocks(this);
//        context = mock(MainActivity.class);
//        pathFinder = new PathFinder(context, roomSource, roomDestination);
//        roomSource = new RoomModel(new Double[]{-73.57907921075821, 45.49702057370776}, "823", "H-8");
//        roomDestination  = new RoomModel(new Double[]{-73.57902321964502, 45.49699848270905}, "821", "H-8");
//    }
//
//    @Test
//    public void getWalkingPointCorrespondingToRoomTest(){
//        List<WalkingPoint> walkingPoints = new ArrayList<>();
//        WalkingPoint walkingPointTest = new WalkingPoint(new Coordinates(-73.57907921075821, 45.49702057370776), "H-8", null, PointType.CLASSROOM);
//        walkingPoints.add(walkingPointTest);
//        assertEquals(pathFinder.getWalkingPointCorrespondingToRoom(roomSource, walkingPoints), walkingPointTest);
//    }
//
////    @Test
////    public void isGoalTest(){
////        assertTrue(pathFinder.isGoal(new WalkingPoint(new Coordinates(-73.57902321964502, 45.49699848270905), "H-8", null, PointType.CLASSROOM)));
////    }
//
//
//}

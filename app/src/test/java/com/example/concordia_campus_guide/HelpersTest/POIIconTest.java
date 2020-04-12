package com.example.concordia_campus_guide.HelpersTest;

import com.example.concordia_campus_guide.ModelTests.TestUtils.TestUtils;
import com.example.concordia_campus_guide.database.AppDatabase;
import com.example.concordia_campus_guide.database.daos.BuildingDao;
import com.example.concordia_campus_guide.helper.POIIcon;
import com.example.concordia_campus_guide.helper.PathFinder;
import com.example.concordia_campus_guide.models.Coordinates;
import com.example.concordia_campus_guide.models.PointType;
import com.example.concordia_campus_guide.models.RoomModel;
import com.example.concordia_campus_guide.models.WalkingPoint;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static org.mockito.Mockito.when;

public class POIIconTest {
    private POIIcon poiIcon;

    @Mock
    AppDatabase mockAppDb;

    @Mock
    BuildingDao mockBuildingDao;


    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        TestUtils testUtils = new TestUtils();

        when(mockAppDb.buildingDao()).thenReturn(mockBuildingDao);
        when(mockAppDb.buildingDao().getBuildingByBuildingCode("H")).thenReturn(testUtils.building1);

        poiIcon = new POIIcon();
    }

    @Test
    public void getCurrentCoordinatesTest(){
        poiIcon.getCurrentCoordinates(mockAppDb);
        assertEquals(new Coordinates(-73.5789475,45.4972685),poiIcon.getCurrentCoordinates(mockAppDb));
    }

    @Test
    public void getPOIinOrderTest(){
        WalkingPoint walkingPoint1 = new WalkingPoint(1,new Coordinates(2,2), "H-9", new ArrayList<>(Arrays.asList(new Integer[]{2})), PointType.NONE,"NONE");
        WalkingPoint walkingPoint2 = new WalkingPoint(2,new Coordinates(2,-2.5), "H-9", new ArrayList<>(Arrays.asList(new Integer[]{1,3})),PointType.NONE,"NONE");
        WalkingPoint walkingPoint3 = new WalkingPoint(3,new Coordinates(1, 1.5), "H-9", new ArrayList<>(Arrays.asList(new Integer[]{2})),PointType.NONE,"NONE");
        List<WalkingPoint> walkingPointList = Arrays.asList(walkingPoint1,walkingPoint2,walkingPoint3);
        assertEquals(walkingPoint3,poiIcon.getPOIinOrder(mockAppDb,walkingPointList,null).peek());
    }
}

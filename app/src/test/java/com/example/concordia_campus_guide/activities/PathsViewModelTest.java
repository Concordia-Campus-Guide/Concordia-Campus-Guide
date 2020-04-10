package com.example.concordia_campus_guide.activities;

import com.example.concordia_campus_guide.adapters.DirectionWrapper;
import com.example.concordia_campus_guide.database.AppDatabase;
import com.example.concordia_campus_guide.global.SelectingToFromState;
import com.example.concordia_campus_guide.models.Building;
import com.example.concordia_campus_guide.models.Coordinates;
import com.example.concordia_campus_guide.models.Direction;
import com.example.concordia_campus_guide.models.PointType;
import com.example.concordia_campus_guide.models.RoomModel;
import com.example.concordia_campus_guide.models.WalkingPoint;
import com.example.concordia_campus_guide.view_models.PathsViewModel;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class PathsViewModelTest {
    private RoomModel fromRoom;
    private RoomModel toRoom;
    private PathsViewModel pathsViewModel;

    @Mock
    AppDatabase mockAppDb;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        pathsViewModel = new PathsViewModel(mockAppDb);
        fromRoom = new RoomModel(new Coordinates(-73.57907921075821, 45.49702057370776), "823", "H-8", "SGW");
        toRoom  = new RoomModel(new Coordinates(-73.57902321964502, 45.49699848270905), "921", "H-9", "SGW");

        SelectingToFromState.setTo(toRoom);
        SelectingToFromState.setFrom(fromRoom);
    }

    @Test
    public void getToTest() {
        assertEquals(toRoom, pathsViewModel.getTo());
    }

    @Test
    public void getFromTest() {
        assertEquals(fromRoom, pathsViewModel.getFrom());
    }

    @Test
    public void getEntranceTest() {
        Building entrance = new Building("H");
        assertEquals(entrance, pathsViewModel.getEntrance(entrance));
    }

    @Test
    public void arePlacesSeparatedByATunnelTest() {
        assertFalse(pathsViewModel.arePlacesSeparatedByATunnel(fromRoom, toRoom));
    }

    @Test
    public void areInSameBuildingTest() {
        assert (pathsViewModel.areInSameBuilding(fromRoom, toRoom));
    }

    @Test
    public void deg2radTest() {
        assertEquals(0,pathsViewModel.deg2rad(0),0.001);
        assertEquals(0.017453292519943295,pathsViewModel.deg2rad(1),0.001);
        assertEquals(Math.PI,pathsViewModel.deg2rad(180),0.001);
    }

    @Test
    public void getDistanceFromLatLonInKmTest() {
        assertEquals(0.43287270110863557, pathsViewModel.getDistanceFromLatLonInKm(45.4972685,-73.5789475,45.493622,-73.577003 ), 0.001);
    }

    @Test
    public void adaptOutdoorDirectionsToInfoCardListTest() {
        List<DirectionWrapper> directionWrappers = getListOfDirectionWrappersTest();
        pathsViewModel.adaptOutdoorDirectionsToInfoCardList(directionWrappers);
        assertEquals(directionWrappers.size(), pathsViewModel.getInfoCardList().size());
    }

    @Test
    public void adaptIndoorDirectionsToInfoCardListTest() {
        List<WalkingPoint> walkingPointList = getWalkingPointListTest();
        pathsViewModel.adaptIndoorDirectionsToInfoCardList(walkingPointList);
        assertEquals(walkingPointList.size(), pathsViewModel.getInfoCardList().size());
    }

    @Test
    public void adaptShuttleToInfoCardListTest(){
        pathsViewModel.adaptShuttleToInfoCardList();
        assertEquals("SHUTTLE", pathsViewModel.getInfoCardList().get(0).getType());
    }

    public static DirectionWrapper getDirectionWrapper1Test(){
        DirectionWrapper directionWrapper1 = new DirectionWrapper();
        Direction direction1 = new Direction();
        direction1.setDuration(60.0);
        direction1.setDescription("Go Left");

        directionWrapper1.setDirection(direction1);
        return directionWrapper1;
    }

    public static DirectionWrapper getDirectionWrapper2Test(){
        DirectionWrapper directionWrapper2 = new DirectionWrapper();
        Direction direction1 = new Direction();
        direction1.setDuration(120.0);
        direction1.setDescription("Go Right");

        directionWrapper2.setDirection(direction1);
        return directionWrapper2;
    }

    public static List<DirectionWrapper> getListOfDirectionWrappersTest(){
        return new ArrayList<>(Arrays.asList(getDirectionWrapper1Test(), getDirectionWrapper2Test()));
    }

    public static com.google.android.gms.maps.model.LatLng getLatLng1(){
        // room 819
        return new com.google.android.gms.maps.model.LatLng(45.496951715566176, -73.5789605230093);
    }

    public static com.google.android.gms.maps.model.LatLng getLatLng2(){
        // room 821
        return new com.google.android.gms.maps.model.LatLng(45.49699848270905,-73.57902321964502);
    }

    public List<WalkingPoint> getWalkingPointListTest(){
        List<WalkingPoint> walkingPointList = new ArrayList<>();
        walkingPointList.add(new WalkingPoint(new Coordinates(10,20),"test",new ArrayList<Integer>(), PointType.CLASSROOM, "test"));
        walkingPointList.add(new WalkingPoint(new Coordinates(10,30),"test",new ArrayList<Integer>(), PointType.ELEVATOR, "test"));
        walkingPointList.add(new WalkingPoint(new Coordinates(10,30),"test",new ArrayList<Integer>(), PointType.STAFF_ELEVATOR, "test"));
        walkingPointList.add(new WalkingPoint(new Coordinates(10,30),"test",new ArrayList<Integer>(), PointType.STAIRS, "test"));
        walkingPointList.add(new WalkingPoint(new Coordinates(10,30),"test",new ArrayList<Integer>(), PointType.ENTRANCE, "test"));
        return walkingPointList;
    }
}
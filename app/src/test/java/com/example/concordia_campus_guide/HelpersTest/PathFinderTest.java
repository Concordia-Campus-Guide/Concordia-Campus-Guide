package com.example.concordia_campus_guide.HelpersTest;

import com.example.concordia_campus_guide.Database.AppDatabase;
import com.example.concordia_campus_guide.Database.Daos.WalkingPointDao;
import com.example.concordia_campus_guide.Helper.PathFinder;
import com.example.concordia_campus_guide.Models.Building;
import com.example.concordia_campus_guide.Models.Coordinates;
import com.example.concordia_campus_guide.Models.Floor;
import com.example.concordia_campus_guide.Models.PointType;
import com.example.concordia_campus_guide.Models.RoomModel;
import com.example.concordia_campus_guide.Models.WalkingPoint;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;
import static org.mockito.Mockito.when;
@RunWith(MockitoJUnitRunner.class)
public class PathFinderTest {
    private WalkingPoint walkingPoint1;
    private WalkingPoint walkingPoint2;
    private WalkingPoint walkingPoint3;
    private WalkingPoint walkingPoint4;
    private WalkingPoint walkingPoint5;
    private List<WalkingPoint> walkingPointList;
    private PathFinder pathFinder;
    private RoomModel room1;
    private RoomModel room2;
    private PathFinder.WalkingPointNode walkingPointNode1;
    private PathFinder.WalkingPointNode walkingPointNode2;

    @Mock
    AppDatabase mockAppDb;

    @Mock
    WalkingPointDao mockWalkingPointDao;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        walkingPoint1 = new WalkingPoint(1,new Coordinates(1, 1), "H-9",new ArrayList<>(Arrays.asList(new Integer[]{2,3,4})), PointType.CLASSROOM, "937");
        walkingPoint2 = new WalkingPoint(2,new Coordinates(2,2), "H-9", new ArrayList<>(Arrays.asList(new Integer[]{1,3})),PointType.NONE,"NONE");
        walkingPoint3 = new WalkingPoint(3,new Coordinates(2,-2.5), "H-9", new ArrayList<>(Arrays.asList(new Integer[]{2,4,5})),PointType.NONE,"NONE");
        walkingPoint4 = new WalkingPoint(4,new Coordinates(2, 2.5), "H-9", new ArrayList<>(Arrays.asList(new Integer[]{1,3,5})),PointType.NONE,"NONE");
        walkingPoint5 = new WalkingPoint(5,new Coordinates(3, 3), "H-9", new ArrayList<>(Arrays.asList(new Integer[]{3,4})),PointType.CLASSROOM,"921");

        room1 = new RoomModel(new Coordinates(1, 1), "937", "H-9", "SGW");
        room2 = new RoomModel(new Coordinates(3, 3), "921", "H-9", "SGW");

        walkingPointList = Arrays.asList(walkingPoint1, walkingPoint2, walkingPoint3, walkingPoint4,walkingPoint5);

        when(mockAppDb.walkingPointDao()).thenReturn(mockWalkingPointDao);
        when(mockWalkingPointDao.getAll()).thenReturn(walkingPointList);
        when(mockAppDb.walkingPointDao().getAllWalkingPointsFromPlace(room1.getFloorCode(), room1.getRoomCode())).thenReturn(Arrays.asList(walkingPoint1));
        when(mockAppDb.walkingPointDao().getAllWalkingPointsFromPlace(room2.getFloorCode(), room2.getRoomCode())).thenReturn(Arrays.asList(walkingPoint5));

        pathFinder = new PathFinder(mockAppDb,room1,room2);

        walkingPointNode1 = pathFinder.new WalkingPointNode(walkingPoint5, null, 22.3, 123.312);
    }

    @Test
    public void isGoalTrueTest(){
        assertTrue(pathFinder.isGoal(walkingPoint5));
    }

    @Test
    public void getWalkingPointCorrespondingToPlaceForBuildingTest(){
        Building building = new Building( new Coordinates(45.49739588, -73.5787094), new ArrayList<String>(Arrays.asList("8","9")), 68, 68, 34, null, "H", null, null, null, null, null);
        WalkingPoint entranceWalkingPoint = new WalkingPoint(6,new Coordinates(-73.57831348, 45.49721303), "H-9", new ArrayList<>(Arrays.asList(new Integer[]{4})),PointType.ENTRANCE,"ENTRANCE");

        when(mockAppDb.walkingPointDao().getAllWalkingPointsFromPlace(building.getBuildingCode()+"-1", "entrance")).thenReturn(Arrays.asList(entranceWalkingPoint));

        assertEquals(entranceWalkingPoint,pathFinder.getWalkingPointCorrespondingToPlace(building));
    }

    @Test
    public void getWalkingPointCorrespondingToPlaceForFloorTest(){
        Floor floorWithCampus=new Floor(new Coordinates(45.496832,-73.578842), "H-9", 0, "SGW");
        WalkingPoint ElevatorWalkingPoint = new WalkingPoint(7,new Coordinates(-73.57876237, 45.49729154), "H-9", new ArrayList<>(Arrays.asList(new Integer[]{2})),PointType.ELEVATOR,"elevators-1");

        when(mockAppDb.walkingPointDao().getAllAccessPointsOnFloor(floorWithCampus.getFloorCode(), PointType.ELEVATOR)).thenReturn(Arrays.asList(ElevatorWalkingPoint));

        assertEquals(ElevatorWalkingPoint,pathFinder.getWalkingPointCorrespondingToPlace(floorWithCampus));
    }
    
    @Test
    public void getPathToDestinationTest(){
        List<WalkingPoint> walkingPointsToDestination = Arrays.asList(walkingPoint5, walkingPoint4, walkingPoint1);
        assertEquals(walkingPointsToDestination,pathFinder.getPathToDestination());
    }

    @Test
    public void populateWalkingPointMapTest(){
        HashMap<Integer, PathFinder.WalkingPointNode> hashMap = pathFinder.populateWalkingPointMap(walkingPointList);
        for(int i =0; i<walkingPointList.size(); i++){
            assertEquals(walkingPointList.get(i).getId(),hashMap.get(i+1).getWalkingPoint().getId());
            assertEquals(walkingPointList.get(i).getFloorCode(),hashMap.get(i+1).getWalkingPoint().getFloorCode());
            assertEquals(walkingPointList.get(i).getPlaceCode(),hashMap.get(i+1).getWalkingPoint().getPlaceCode());
            assertEquals(walkingPointList.get(i).getCoordinate(),hashMap.get(i+1).getWalkingPoint().getCoordinate());
            assertTrue(verifyIds(walkingPointList.get(i).getConnectedPointsId(),hashMap.get(i+1).getWalkingPoint().getConnectedPointsId()));
            assertEquals(walkingPointList.get(i).getPointType(),hashMap.get(i+1).getWalkingPoint().getPointType());
        }
    }

    //WalkingNode Inner Class Tests:
    @Test
    public void getAndSetWalkingPointTest(){
        assertEquals(walkingPoint5,walkingPointNode1.getWalkingPoint());
        walkingPointNode1.setWalkingPoint(walkingPoint2);
        assertEquals(walkingPoint2, walkingPointNode1.getWalkingPoint());
    }

    @Test
    public void getAndSetHeuristicTest(){
        assertEquals(22.3, walkingPointNode1.getHeuristic());
        walkingPointNode1.setHeuristic(1102.33);
        assertEquals(1102.33, walkingPointNode1.getHeuristic());
    }

    @Test
    public void getAndSetCostTest(){
        assertEquals(123.312, walkingPointNode1.getCost());
        walkingPointNode1.setCost(1102.33);
        assertEquals(1102.33, walkingPointNode1.getCost());
    }

    @Test
    public void compareWalkingPointComparatorTest(){
        PathFinder.WalkingPointComparator walkingPointComparator = pathFinder.new WalkingPointComparator();
        walkingPointNode2 = pathFinder.new WalkingPointNode(walkingPoint1, walkingPointNode1, 31.3, 993.312);
        assertEquals(-1, walkingPointComparator.compare(walkingPointNode1,walkingPointNode2));
        assertEquals(1,walkingPointComparator.compare(walkingPointNode2,walkingPointNode1));
        assertEquals(0,walkingPointComparator.compare(walkingPointNode1,walkingPointNode1));
    }


    @Test
    public void addNearestWalkingPointsTest(){
        pathFinder.addNearestWalkingPoints(walkingPointNode1);
        assertEquals(1.118033988749895,pathFinder.getWalkingPointsToVisit().peek().getHeuristic());
    }


    private boolean verifyIds(List<Integer>walkingPointsIds, List<Integer> walkingPointNodeList){
        for(int i=0; i < walkingPointsIds.size(); i++){
            if(walkingPointsIds.get(i).equals(walkingPointList.get(i))){
                return false;
            }
        }
        return true;
    }
}

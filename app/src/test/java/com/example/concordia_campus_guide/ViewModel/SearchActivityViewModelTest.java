package com.example.concordia_campus_guide.ViewModel;

import android.location.Location;

import com.example.concordia_campus_guide.Activities.SearchActivityViewModel;
import com.example.concordia_campus_guide.Database.AppDatabase;
import com.example.concordia_campus_guide.Database.Daos.BuildingDao;
import com.example.concordia_campus_guide.Database.Daos.FloorDao;
import com.example.concordia_campus_guide.Database.Daos.RoomDao;
import com.example.concordia_campus_guide.Models.Building;
import com.example.concordia_campus_guide.Models.Floor;
import com.example.concordia_campus_guide.Models.RoomModel;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Arrays;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SearchActivityViewModelTest {

    @Mock
    AppDatabase mockAppDb;
    SearchActivityViewModel mViewModel;

    @Mock
    BuildingDao mockBuildingDao;

    @Mock
    FloorDao mockFloorDao;

    @Mock
    RoomDao mockRoomDao;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        when(mockAppDb.buildingDao()).thenReturn(mockBuildingDao);
        when(mockAppDb.floorDao()).thenReturn(mockFloorDao);
        when(mockAppDb.roomDao()).thenReturn(mockRoomDao);
        when(mockRoomDao.getAll()).thenReturn(new ArrayList<RoomModel>(Arrays.asList(
                new RoomModel(new Double[]{}, "111", "H-9"), new RoomModel(new Double[]{}, "222", "H-8")
        )));
        when(mockFloorDao.getAll()).thenReturn(new ArrayList<Floor>(Arrays.asList(
                new Floor(new Double[]{}, "H-9", 0), new Floor(new Double[]{}, "H-8", 0)
        )));
        when(mockBuildingDao.getAll()).thenReturn(new ArrayList<Building>(Arrays.asList(
                new Building("H"), new Building("MB")
        )));

        mViewModel = new SearchActivityViewModel(mockAppDb);

    }

    @Test
    public void getBuildings(){
        assertEquals(mockBuildingDao.getAll(), mViewModel.getBuildings());
    }

    @Test
    public void getFloors(){
        assertEquals(mockFloorDao.getAll(), mViewModel.getFloors());
    }

    @Test
    public void getRooms(){
        assertEquals(mockRoomDao.getAll(), mViewModel.getRooms());
    }

    @Test
    public void getAllPlaces(){
        assertTrue(mViewModel.getAllPlaces().containsAll(mockBuildingDao.getAll()) &&
                mViewModel.getAllPlaces().containsAll(mockRoomDao.getAll()) &&
                mViewModel.getAllPlaces().containsAll(mockFloorDao.getAll()));
    }

    @Test
    public void getSetMyCurrentLocation(){
        Location sampleLocation = new Location("some provider");
        mViewModel.setMyCurrentLocation(sampleLocation);

        assertEquals(mViewModel.getMyCurrentLocation(), sampleLocation);
    }

    @Test
    public void getSetFromId(){
        Long sampleLong = new Long(234234234234L);
        mViewModel.setFromId(sampleLong);

        assertEquals(mViewModel.getFromId(), sampleLong);
    }

    @Test
    public void getSetToId(){
        Long sampleLong = new Long(4563456345634L);
        mViewModel.setToId(sampleLong);

        assertEquals(mViewModel.getToId(), sampleLong);
    }



}

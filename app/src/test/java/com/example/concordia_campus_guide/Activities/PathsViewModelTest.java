package com.example.concordia_campus_guide.Activities;

import com.example.concordia_campus_guide.Database.AppDatabase;
import com.example.concordia_campus_guide.Global.SelectingToFromState;
import com.example.concordia_campus_guide.Models.Building;
import com.example.concordia_campus_guide.Models.Coordinates;
import com.example.concordia_campus_guide.Models.RoomModel;
import com.example.concordia_campus_guide.ViewModels.PathsViewModel;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

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
    public void getTo() {
        assertEquals(toRoom, pathsViewModel.getTo());
    }

    @Test
    public void getFrom() {
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
}
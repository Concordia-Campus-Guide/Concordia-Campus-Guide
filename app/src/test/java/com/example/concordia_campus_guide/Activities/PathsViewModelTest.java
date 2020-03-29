package com.example.concordia_campus_guide.Activities;

import com.example.concordia_campus_guide.Global.SelectingToFromState;
import com.example.concordia_campus_guide.Models.Coordinates;
import com.example.concordia_campus_guide.Models.RoomModel;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;



import static org.junit.Assert.assertEquals;

public class PathsViewModelTest {
    private RoomModel fromRoom;
    private RoomModel toRoom;
    private PathsViewModel pathsViewModel;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        pathsViewModel = new PathsViewModel();
        fromRoom = new RoomModel(new Coordinates(-73.57907921075821, 45.49702057370776), "823", "H-8");
        toRoom  = new RoomModel(new Coordinates(-73.57902321964502, 45.49699848270905), "921", "H-9");

        SelectingToFromState.setTo(toRoom);
        SelectingToFromState.setFrom(fromRoom);
    }

    @Test
    public void getTo() {
        assertEquals("921",toRoom.getRoomCode());
    }

    @Test
    public void getFrom() {
        assertEquals("823", fromRoom.getRoomCode());
    }
}
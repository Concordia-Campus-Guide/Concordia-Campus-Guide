package com.example.concordia_campus_guide.HelpersTest;

import com.example.concordia_campus_guide.Activities.MainActivity;
import com.example.concordia_campus_guide.Database.AppDatabase;
import com.example.concordia_campus_guide.Database.Daos.RoomDao;
import com.example.concordia_campus_guide.Helper.Notification;
import com.example.concordia_campus_guide.Models.Coordinates;
import com.example.concordia_campus_guide.Models.RoomModel;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

public class NotificationTest {
    private Notification notification;

    @Mock
    AppDatabase mockAppDatabase;

    @Mock
    MainActivity mainActivity;

    @Mock
    RoomDao mockRoomDao;

    @Before
    public void init(){
        MockitoAnnotations.initMocks(this);
        notification = new Notification(mainActivity,mockAppDatabase);
        when(mockAppDatabase.roomDao()).thenReturn(mockRoomDao);
        when(mockRoomDao.getRoomByRoomCodeAndFloorCode("927","H-9")).thenReturn(
                new RoomModel(new Coordinates(0, 0), "111", "H-9")
        );
        when(mockRoomDao.getRoomByRoomCodeAndFloorCode("1027","H-10")).thenReturn(
                new RoomModel(new Coordinates(0, 0), "1027", "H-10")
        );
    }
    @Test
    public void getRoomTest(){
        RoomModel expectedRoom = new RoomModel(new Coordinates(0, 0), "111", "H-9");
        assertEquals(expectedRoom.getFloorCode(), notification.getRoom("H-9, 927").getFloorCode());
    }

    @Test
    public void getRoom10thFloorTest(){
        RoomModel expectedRoom = new RoomModel(new Coordinates(0, 0), "1027", "H-10");
        assertEquals(expectedRoom.getFloorCode(), notification.getRoom("H-10, 1027").getFloorCode());
    }

    @Test
    public void getRoomNullTest(){
        assertNull(notification.getRoom(null));
    }

    @Test
    public void roomExistsInDbTest(){
        assertTrue(notification.roomExistsInDb("H-10, 1027"));
    }

    @Test
    public void roomExistsInDbNotExistingTest(){
        assertFalse(notification.roomExistsInDb("H-12, 132"));
    }
}

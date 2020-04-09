package com.example.concordia_campus_guide.HelpersTest;

import com.example.concordia_campus_guide.activities.MainActivity;
import com.example.concordia_campus_guide.database.AppDatabase;
import com.example.concordia_campus_guide.database.Daos.RoomDao;
import com.example.concordia_campus_guide.helper.Notification;
import com.example.concordia_campus_guide.models.Coordinates;
import com.example.concordia_campus_guide.models.RoomModel;

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
                new RoomModel(new Coordinates(0, 0), "111", "H-9", "SGW")
        );
        when(mockRoomDao.getRoomByRoomCodeAndFloorCode("1027","H-10")).thenReturn(
                new RoomModel(new Coordinates(0, 0), "1027", "H-10", "SGW")
        );
    }
    @Test
    public void getRoomTest(){
        RoomModel expectedRoom = new RoomModel(new Coordinates(0, 0), "111", "H-9", "SGW");
        assertEquals(expectedRoom.getFloorCode(), notification.getRoom("H-9, 927").getFloorCode());
    }

    @Test
    public void getRoom10thFloorTest(){
        RoomModel expectedRoom = new RoomModel(new Coordinates(0, 0), "1027", "H-10", "SGW");
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

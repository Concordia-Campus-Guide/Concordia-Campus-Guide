package com.example.concordia_campus_guide.Activities;

import com.example.concordia_campus_guide.Global.SelectingToFromState;
import com.example.concordia_campus_guide.Models.Place;
import com.example.concordia_campus_guide.Models.RoomModel;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.junit.Assert.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest(SelectingToFromState.class)
public class PathsViewModelTest {
    @Mock
    SelectingToFromState selectingToFromState;

    @Mock
    RoomModel roomModel;

    PathsViewModel pathsViewModel;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        pathsViewModel = new PathsViewModel();
        RoomModel room = new RoomModel();
        room.setRoomCode("927");

        PowerMockito.mockStatic(SelectingToFromState.class);
        PowerMockito.when(SelectingToFromState.getTo()).thenReturn(room);
        PowerMockito.when(SelectingToFromState.getFrom()).thenReturn(room);
    }

    @Test
    public void getTo() {
        RoomModel to = (RoomModel) pathsViewModel.getTo();
        assertEquals("927", to.getRoomCode());
    }

    @Test
    public void getFrom() {
        RoomModel from = (RoomModel) pathsViewModel.getFrom();
        assertEquals("927", from.getRoomCode());
    }
}
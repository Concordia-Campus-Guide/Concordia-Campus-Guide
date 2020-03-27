package com.example.concordia_campus_guide.Activities;

import com.example.concordia_campus_guide.Global.SelectingToFromState;
import com.example.concordia_campus_guide.Models.Place;
import com.example.concordia_campus_guide.Models.RoomModel;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PathsViewModelTest {
    @Mock
    SelectingToFromState selectingToFromState;

    @Mock
    RoomModel roomModel;

    PathsViewModel pathsViewModel;

    @Before
    public void setup(){
//        MockitoAnnotations.initMocks(this);
//        pathsViewModel = new PathsViewModel();
//        when(roomModel.getRoomCode()).thenReturn("963");
    }

    @Test
    public void getTo() {
//        when(selectingToFromState.getTo()).thenReturn(roomModel);
//        RoomModel testRoom = (RoomModel) pathsViewModel.getTo();
//
//        assertEquals(testRoom.getRoomCode(), "963");
    }

    @Test
    public void getFrom() {
//        when(selectingToFromState.getFrom()).thenReturn(roomModel);
//        RoomModel testRoom = (RoomModel) pathsViewModel.getFrom();
//        assertEquals(testRoom.getRoomCode(), "963");
    }
}
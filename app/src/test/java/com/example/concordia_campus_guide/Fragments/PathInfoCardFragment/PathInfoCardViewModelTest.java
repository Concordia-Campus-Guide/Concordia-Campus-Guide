package com.example.concordia_campus_guide.Fragments.PathInfoCardFragment;

import com.example.concordia_campus_guide.Adapters.DirectionWrapper;
import com.example.concordia_campus_guide.ModelTests.TestUtils.TestUtils;
import com.example.concordia_campus_guide.Models.Direction;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class PathInfoCardViewModelTest {
    private PathInfoCardViewModel pathInfoCardViewModel;
    private PathInfoCardViewModel pathInfoCardViewModelWithTotalDuration;


    @Before
    public void init(){
        pathInfoCardViewModel = new PathInfoCardViewModel();

    }

    @Test
    public void getTotalDuration() {
        assertEquals(0,pathInfoCardViewModel.getTotalDuration(),0.001);
    }

    @Test
    public void deg2rad() {
        assertEquals(0,pathInfoCardViewModel.deg2rad(0),0.001);
        assertEquals(0.017453292519943295,pathInfoCardViewModel.deg2rad(1),0.001);
        assertEquals(Math.PI,pathInfoCardViewModel.deg2rad(180),0.001);
    }

    @Test
    public void getDistanceFromLatLonInKm() {
        assertEquals(0.43287270110863557, pathInfoCardViewModel.getDistanceFromLatLonInKm(45.4972685,-73.5789475,45.493622,-73.577003 ), 0.001);
    }

    @Test
    public void createOutdoorDirectionsList() {
        List<Direction> tempOutdoorDirection = pathInfoCardViewModel.createOutdoorDirectionsList(TestUtils.getListOfDirectionWrappers());
        assertEquals(3, pathInfoCardViewModel.getTotalDuration(), 0.001);
        assertEquals(2, tempOutdoorDirection.size());
    }

    @Test
    public void createIndoorDirection() {
        Direction tempIndoorDirection = pathInfoCardViewModel.createIndoorDirection(TestUtils.getLatLng1(), TestUtils.getLatLng2(),TestUtils.getDirectionDescription(), 10.0, 3.0 );
        assertEquals(3.0, tempIndoorDirection.getDuration(),0.01);
    }
}
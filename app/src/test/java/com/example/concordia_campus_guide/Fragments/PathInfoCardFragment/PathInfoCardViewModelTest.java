package com.example.concordia_campus_guide.Fragments.PathInfoCardFragment;

import com.example.concordia_campus_guide.Adapters.DirectionWrapper;
import com.example.concordia_campus_guide.Models.Direction;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

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
        List<Direction> tempOutdoorDirection = pathInfoCardViewModel.createOutdoorDirectionsList(getListOfDirectionWrappers());
        assertEquals(3, pathInfoCardViewModel.getTotalDuration(), 0.001);
        assertEquals(2, tempOutdoorDirection.size());
    }

    @Test
    public void createIndoorDirection() {
        Direction tempIndoorDirection = pathInfoCardViewModel.createIndoorDirection(getLatLng1(), getLatLng2(),getDirectionDescription(), 10.0, 3.0 );
        assertEquals(3.0, tempIndoorDirection.getDuration(),0.01);
    }

    public static DirectionWrapper getDirectionWrapper1(){
        DirectionWrapper directionWrapper1 = new DirectionWrapper();
        Direction direction1 = new Direction();
        direction1.setDuration(60.0);
        direction1.setDescription("Go Left");

        directionWrapper1.setDirection(direction1);
        return directionWrapper1;
    }

    public static DirectionWrapper getDirectionWrapper2(){
        DirectionWrapper directionWrapper2 = new DirectionWrapper();
        Direction direction1 = new Direction();
        direction1.setDuration(120.0);
        direction1.setDescription("Go Right");

        directionWrapper2.setDirection(direction1);
        return directionWrapper2;
    }

    public static List<DirectionWrapper> getListOfDirectionWrappers(){
        return new ArrayList<>(Arrays.asList(getDirectionWrapper1(), getDirectionWrapper2()));
    }

    public static com.google.android.gms.maps.model.LatLng getLatLng1(){
        // room 819
        return new com.google.android.gms.maps.model.LatLng(45.496951715566176, -73.5789605230093);
    }

    public static com.google.android.gms.maps.model.LatLng getLatLng2(){
        // room 821
        return new com.google.android.gms.maps.model.LatLng(45.49699848270905,-73.57902321964502);
    }

    public static String getDirectionDescription(){
        return "Go Left";
    }
}
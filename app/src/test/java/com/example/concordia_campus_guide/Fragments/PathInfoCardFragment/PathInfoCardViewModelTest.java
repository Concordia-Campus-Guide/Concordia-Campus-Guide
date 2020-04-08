package com.example.concordia_campus_guide.Fragments.PathInfoCardFragment;

import com.example.concordia_campus_guide.R;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PathInfoCardViewModelTest {
    private PathInfoCardViewModel pathInfoCardViewModel;


    @Before
    public void init(){
        pathInfoCardViewModel = new PathInfoCardViewModel();
    }

    @Test
    public void getDefaultIcon(){
        int icon = pathInfoCardViewModel.getIcon("TEST");
        assertEquals(-1, icon);
    }

    @Test
    public void getDrivingIcon(){
        int icon = pathInfoCardViewModel.getIcon("DRIVING");
        assertEquals(R.drawable.ic_directions_car_red, icon);
    }

    @Test
    public void getWalkingIcon(){
        int icon = pathInfoCardViewModel.getIcon("WALKING");
        assertEquals(R.drawable.ic_directions_walk_red, icon);
    }

    @Test
    public void getBicyclingIcon(){
        int icon = pathInfoCardViewModel.getIcon("BICYCLING");
        assertEquals(R.drawable.ic_directions_bike_black_24dp, icon);
    }

    @Test
    public void getElevatorIcon(){
        int icon = pathInfoCardViewModel.getIcon("ELEVATOR");
        assertEquals(R.drawable.ic_elevator_white, icon);
    }

    @Test
    public void getStairsIcon(){
        int icon = pathInfoCardViewModel.getIcon("STAIRS");
        assertEquals(R.drawable.ic_stairs_white, icon);
    }

    @Test
    public void getShuttleIcon(){
        int icon = pathInfoCardViewModel.getIcon("SHUTTLE");
        assertEquals(R.drawable.ic_shuttle_red, icon);
    }
}
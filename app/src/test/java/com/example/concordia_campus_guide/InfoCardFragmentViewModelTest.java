package com.example.concordia_campus_guide;

import android.app.Application;

import com.example.concordia_campus_guide.InfoCardFragment.InfoCardFragmentViewModel;
import com.example.concordia_campus_guide.LocationFragment.LocationFragmentViewModel;
import com.example.concordia_campus_guide.Models.Building;
import com.example.concordia_campus_guide.Models.Buildings;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import static junit.framework.TestCase.*;

public class InfoCardFragmentViewModelTest {
    private InfoCardFragmentViewModel viewModel;

    @Before
    public void init()
    {
        MockitoAnnotations.initMocks(this);
        Application application = Mockito.mock(Application.class);
        viewModel = new InfoCardFragmentViewModel(application);
    }

    @Test
    public void getBuildingsTest()
    {
        viewModel.setBuildings(new Buildings());
        Buildings buildings = viewModel.getBuildings();
        assertNotNull(buildings);
    }

    @Test
    public void setBuildingsTest()
    {
        Buildings buildings = new Buildings();
        viewModel.setBuildings(buildings);
        assertEquals(viewModel.getBuildings(), buildings);
    }
}

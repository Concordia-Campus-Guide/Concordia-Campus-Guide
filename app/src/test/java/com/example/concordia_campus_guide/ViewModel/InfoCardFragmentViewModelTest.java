package com.example.concordia_campus_guide.ViewModel;

import com.example.concordia_campus_guide.database.AppDatabase;
import com.example.concordia_campus_guide.database.daos.BuildingDao;
import com.example.concordia_campus_guide.view_models.InfoCardFragmentViewModel;
import com.example.concordia_campus_guide.models.Building;
import com.example.concordia_campus_guide.models.Place;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class InfoCardFragmentViewModelTest {

    @Mock
    AppDatabase mockAppDb;
    InfoCardFragmentViewModel mViewModel;

    @Mock
    BuildingDao mockBuildingDao;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mViewModel = new InfoCardFragmentViewModel(mockAppDb);
    }

    @Test
    public void setBuilding() {
        Building sampleBuilding = new Building("H");
        when(mockAppDb.buildingDao()).thenReturn(mockBuildingDao);
        when(mockBuildingDao.getBuildingByBuildingCode("H")).thenReturn(sampleBuilding);

        mViewModel.setBuilding("H");
        assertEquals(sampleBuilding, mViewModel.getBuilding());
    }

    @Test
    public void getBuilding(){
        Building sampleBuilding = new Building("H");
        mViewModel.setPlace((Place) sampleBuilding);

        assertEquals(sampleBuilding, mViewModel.getBuilding());
    }

    @Test
    public void setPlace(){
        Building sampleBuilding = new Building("H");
        mViewModel.setPlace(sampleBuilding);

        assertEquals(sampleBuilding, mViewModel.getBuilding());
    }
}

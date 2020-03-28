package com.example.concordia_campus_guide.ViewModel;

import com.example.concordia_campus_guide.Activities.RoutesActivityViewModel;
import com.example.concordia_campus_guide.Database.AppDatabase;
import com.example.concordia_campus_guide.Models.Building;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import static junit.framework.TestCase.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class RoutesActivityViewModelTest {

    @Mock
    AppDatabase mockAppDb;
    RoutesActivityViewModel mViewModel;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        mViewModel = new RoutesActivityViewModel(mockAppDb);
    }

    @Test
    public void getSetTo(){
        Building sampleBuilding = new Building("H");
        mViewModel.setTo(sampleBuilding);

        assertEquals(mViewModel.getTo(), sampleBuilding);
    }

    @Test
    public void getSetFrom(){
        Building sampleBuilding = new Building("H");
        mViewModel.setFrom(sampleBuilding);

        assertEquals(mViewModel.getFrom(), sampleBuilding);
    }
}

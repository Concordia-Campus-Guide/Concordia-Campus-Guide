package com.example.concordia_campus_guide.ViewModel;

import com.example.concordia_campus_guide.database.AppDatabase;
import com.example.concordia_campus_guide.models.Coordinates;
import com.example.concordia_campus_guide.models.RoomModel;
import com.example.concordia_campus_guide.view_models.SmallInfoCardFragmentViewModel;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.assertEquals;

public class SmallInfoCardFragmentViewModelTest {
    private SmallInfoCardFragmentViewModel smallInfoCardFragmentViewModel;

    @Mock
    AppDatabase mockAppDb;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getAndSetPlaceTest(){
        smallInfoCardFragmentViewModel = new SmallInfoCardFragmentViewModel(mockAppDb);
        RoomModel room = new RoomModel(new Coordinates(-73.57907921075821, 45.49702057370776), "823", "H-8", "SGW");
        smallInfoCardFragmentViewModel.setPlace(room);
        assertEquals(room, smallInfoCardFragmentViewModel.getPlace());
    }
}

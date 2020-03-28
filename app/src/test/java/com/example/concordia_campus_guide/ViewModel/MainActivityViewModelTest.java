package com.example.concordia_campus_guide.ViewModel;

import com.example.concordia_campus_guide.Activities.MainActivityViewModel;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import static junit.framework.TestCase.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MainActivityViewModelTest {
    private MainActivityViewModel mainActivityViewModel;

    @Before
    public void init(){
        mainActivityViewModel = new MainActivityViewModel();
    }

    @Test
    public void cleanTimeDisplayLongerThanOneHoursTest(){
        long date = 15852069;
        String dateString = mainActivityViewModel.timeUntilMethod(date);
        String expectedValue ="04 hours and 24 minutes";
        assertEquals( expectedValue, mainActivityViewModel.cleanTimeDisplay(dateString));
    }

    @Test
    public void cleanTimeDisplayLessThanOneHoursTest(){
        long date = 158520;
        String dateString = mainActivityViewModel.timeUntilMethod(date);
        String expectedValue =" 02 minutes";
        assertEquals(expectedValue, mainActivityViewModel.cleanTimeDisplay(dateString));
    }

    @Test
    public void timeUtilMethod(){
        long date = 158520;
        String expectedValue ="00 hours and 02 minutes";
        assertEquals(expectedValue, mainActivityViewModel.timeUntilMethod(date));
    }
}

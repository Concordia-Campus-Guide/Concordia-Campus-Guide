package com.example.concordia_campus_guide.InstrumentalTests;

import com.example.concordia_campus_guide.Activities.MainActivity;
import com.example.concordia_campus_guide.ViewModels.RoutesActivityViewModel;
import com.example.concordia_campus_guide.ViewModels.SearchActivityViewModel;
import com.example.concordia_campus_guide.ViewModels.InfoCardFragmentViewModel;
import com.example.concordia_campus_guide.ViewModels.LocationFragmentViewModel;
import com.example.concordia_campus_guide.Helper.ViewModelFactory;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;
import static org.junit.Assert.assertNotNull;

@RunWith(AndroidJUnit4.class)
public class ViewModelFactoryInstrumentalTest {
    private ViewModelFactory viewModelFactory;

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(MainActivity.class);

    @Before
    public void init() {
        viewModelFactory = new ViewModelFactory(mActivityRule.getActivity().getApplication());
    }

    @Test
    public void createTest(){
        assertNotNull(viewModelFactory.create(SearchActivityViewModel.class));
        assertNotNull(viewModelFactory.create(InfoCardFragmentViewModel.class));
        assertNotNull(viewModelFactory.create(RoutesActivityViewModel.class));
        assertNotNull(viewModelFactory.create(LocationFragmentViewModel.class));
    }

}

package com.example.concordia_campus_guide.InstrumentalTests;
import android.content.Context;
import com.example.concordia_campus_guide.Activities.MainActivity;
import com.example.concordia_campus_guide.InfoCardFragment.InfoCardFragmentViewModel;
import com.example.concordia_campus_guide.Models.Building;
import com.example.concordia_campus_guide.Models.Buildings;
import org.junit.Rule;
import org.junit.Test;
import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;
import androidx.test.filters.SmallTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;
import org.junit.Before;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
@SmallTest
public class InfoCardFragmentViewModelTest {
    private InfoCardFragmentViewModel viewModel;

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(MainActivity.class);

    @Before
    public void init() {
        viewModel = new InfoCardFragmentViewModel();
    }
}
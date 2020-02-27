package com.example.concordia_campus_guide;

import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
public class LocationFragmentUITest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule =
            new ActivityTestRule<>(MainActivity.class);

    @Test
    public void polygonVisibleTest(){
        onView(withId(R.id.mapView));
        onView(withId(R.id.SGWBtn)).perform(click());
        onView(withId(R.drawable.ev));
        onView(withId(R.id.loyolaBtn)).perform(click());
        onView(withId(R.drawable.ad));

    }

}
package com.example.concordia_campus_guide.Activities;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.espresso.ViewInteraction;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.rule.GrantPermissionRule;
import androidx.test.runner.AndroidJUnit4;
import androidx.test.uiautomator.UiDevice;
import androidx.test.uiautomator.UiObject;
import androidx.test.uiautomator.UiObjectNotFoundException;
import androidx.test.uiautomator.UiSelector;

import com.example.concordia_campus_guide.EspressoHelpers;
import com.example.concordia_campus_guide.R;
import com.example.concordia_campus_guide.activities.MainActivity;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static org.hamcrest.Matchers.allOf;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class CampusSwitcherTest {

    private UiDevice device;

    @Before
    public void init() { device = UiDevice.getInstance(getInstrumentation()); }

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Rule
    public GrantPermissionRule mGrantPermissionRule =
            GrantPermissionRule.grant(
                    "android.permission.ACCESS_FINE_LOCATION",
                    "android.permission.ACCESS_COARSE_LOCATION",
                    "android.permission.WRITE_CALENDAR",
                    "android.permission.READ_CALENDAR");

    @Test
    public void campusSwitcherTest() throws UiObjectNotFoundException {
        EspressoHelpers.cancelNotificationIfExists();

        ViewInteraction materialButton = onView(
                allOf(withId(R.id.loyolaBtn), withText("Loyola"),
                        childAtPosition(
                                allOf(withId(R.id.campusSwitchBtnContainer),
                                        childAtPosition(
                                                withId(R.id.topMapContainer),
                                                0)),
                                1),
                        isDisplayed()));
        materialButton.perform(click());

        UiObject marker = device.findObject(new UiSelector().descriptionContains("VL"));
        try {
            int bottom = marker.getVisibleBounds().bottom;
            marker.click();
        } catch (UiObjectNotFoundException e) {
            e.printStackTrace();
        }
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //once clicked, ensures that the info card is visible. it is through the click and this assertion that we make
        //sure that the campus switcher works correctly
        ViewInteraction textView = onView(
                allOf(withId(R.id.info_card_title), withText("Vanier Library Building"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.bottom_card_frame),
                                        0),
                                0),
                        isDisplayed()));
        textView.check(matches(withText("Vanier Library Building")));

        ViewInteraction materialButton2 = onView(
                allOf(withId(R.id.SGWBtn), withText("SGW"),
                        childAtPosition(
                                allOf(withId(R.id.campusSwitchBtnContainer),
                                        childAtPosition(
                                                withId(R.id.topMapContainer),
                                                0)),
                                0),
                        isDisplayed()));
        materialButton2.perform(click());



        marker = device.findObject(new UiSelector().descriptionContains("H"));
        try {
            marker.click();
        } catch (UiObjectNotFoundException e) {
            e.printStackTrace();
        }
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        textView = onView(
                allOf(withId(R.id.info_card_title), withText("Henry F. Hall Building"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.bottom_card_frame),
                                        0),
                                0),
                        isDisplayed()));
        textView.check(matches(withText("Henry F. Hall Building")));
    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}
//package com.example.concordia_campus_guide.activities;
//
//
//import android.view.View;
//import android.view.ViewGroup;
//import android.view.ViewParent;
//
//import androidx.test.espresso.DataInteraction;
//import androidx.test.espresso.ViewInteraction;
//import androidx.test.filters.LargeTest;
//import androidx.test.rule.ActivityTestRule;
//import androidx.test.rule.GrantPermissionRule;
//import androidx.test.runner.AndroidJUnit4;
//import androidx.test.uiautomator.UiDevice;
//import androidx.test.uiautomator.UiObject;
//import androidx.test.uiautomator.UiObjectNotFoundException;
//import androidx.test.uiautomator.UiSelector;
//
//import com.example.concordia_campus_guide.EspressoHelpers;
//import com.example.concordia_campus_guide.R;
//import com.example.concordia_campus_guide.activities.MainActivity;
//
//import org.hamcrest.Description;
//import org.hamcrest.Matcher;
//import org.hamcrest.TypeSafeMatcher;
//import org.hamcrest.core.IsInstanceOf;
//import org.junit.Before;
//import org.junit.Rule;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//
//import static androidx.test.espresso.Espresso.onData;
//import static androidx.test.espresso.Espresso.onView;
//import static androidx.test.espresso.action.ViewActions.click;
//import static androidx.test.espresso.assertion.ViewAssertions.matches;
//import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
//import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
//import static androidx.test.espresso.matcher.ViewMatchers.withId;
//import static androidx.test.espresso.matcher.ViewMatchers.withText;
//import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
//import static org.hamcrest.Matchers.allOf;
//import static org.hamcrest.Matchers.anything;
//
//@LargeTest
//@RunWith(AndroidJUnit4.class)
//public class FloorPickerAndMarkersHallTest {
//
//
//    private UiDevice device;
//
//    @Before
//    public void init() { device = UiDevice.getInstance(getInstrumentation()); }
//
//    @Rule
//    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);
//
//    @Rule
//    public GrantPermissionRule mGrantPermissionRule =
//            GrantPermissionRule.grant(
//                    "android.permission.ACCESS_FINE_LOCATION",
//                    "android.permission.ACCESS_COARSE_LOCATION",
//                    "android.permission.WRITE_CALENDAR",
//                    "android.permission.READ_CALENDAR");
//
//    @Test
//    public void floorPickerAndMarkersHallTest() {
//        EspressoHelpers.cancelNotificationIfExists();
//
//        ViewInteraction materialButton = onView(
//                allOf(withId(R.id.SGWBtn), withText("SGW"),
//                        childAtPosition(
//                                allOf(withId(R.id.campusSwitchBtnContainer),
//                                        childAtPosition(
//                                                withId(R.id.topMapContainer),
//                                                0)),
//                                0),
//                        isDisplayed()));
//        materialButton.perform(click());
//
//        android.os.SystemClock.sleep(1000);
//
//        UiObject marker = device.findObject(new UiSelector().descriptionContains("H"));
//        try {
//            marker.click();
//        } catch (UiObjectNotFoundException e) {
//            e.printStackTrace();
//        }
//
//        android.os.SystemClock.sleep(1000);
//
//        ViewInteraction button = onView(
//                allOf(childAtPosition(
//                        allOf(withId(R.id.FloorPickerGv),
//                                childAtPosition(
//                                        withId(R.id.locationFragment),
//                                        3)),
//                        3),
//                        isDisplayed()));
//        button.check(matches(isDisplayed()));
//
//        ViewInteraction button2 = onView(
//                allOf(childAtPosition(
//                        allOf(withId(R.id.FloorPickerGv),
//                                childAtPosition(
//                                        withId(R.id.locationFragment),
//                                        3)),
//                        2),
//                        isDisplayed()));
//        button2.check(matches(isDisplayed()));
//
//        ViewInteraction button3 = onView(
//                allOf(childAtPosition(
//                        allOf(withId(R.id.FloorPickerGv),
//                                childAtPosition(
//                                        withId(R.id.locationFragment),
//                                        3)),
//                        1),
//                        isDisplayed()));
//        button3.check(matches(isDisplayed()));
//
//        ViewInteraction button4 = onView(
//                allOf(childAtPosition(
//                        allOf(withId(R.id.FloorPickerGv),
//                                childAtPosition(
//                                        withId(R.id.locationFragment),
//                                        3)),
//                        0),
//                        isDisplayed()));
//        button4.check(matches(isDisplayed()));
//
//        android.os.SystemClock.sleep(1000);
//
//        UiObject marker2 = device.findObject(new UiSelector().descriptionContains("967"));
//        try {
//            marker2.click();
//        } catch (UiObjectNotFoundException e) {
//            e.printStackTrace();
//        }
//
//        android.os.SystemClock.sleep(1000);
//
//        ViewInteraction textView = onView(
//                allOf(withId(R.id.info_card_title), withText("H-9 967"),
//                        childAtPosition(
//                                allOf(withId(R.id.infocardLinearLayout),
//                                        childAtPosition(
//                                                withId(R.id.bottom_card_frame),
//                                                0)),
//                                0),
//                        isDisplayed()));
//        textView.check(matches(withText("H-9 967")));
//
//        button = onView(
//                allOf(withId(R.id.directions),
//                        childAtPosition(
//                                childAtPosition(
//                                        withId(R.id.infocardLinearLayout),
//                                        1),
//                                0),
//                        isDisplayed()));
//        button.check(matches(isDisplayed()));
//
//        button = onView(
//                allOf(withId(R.id.directions),
//                        childAtPosition(
//                                childAtPosition(
//                                        withId(R.id.infocardLinearLayout),
//                                        1),
//                                0),
//                        isDisplayed()));
//        button.check(matches(isDisplayed()));
//
//        DataInteraction button5 = onData(anything())
//                .inAdapterView(allOf(withId(R.id.FloorPickerGv),
//                        childAtPosition(
//                                withId(R.id.locationFragment),
//                                3)))
//                .atPosition(2);
//        button5.perform(click());
//
//        android.os.SystemClock.sleep(1000);
//
//        //NOTE THE DISCREPANCY BETWEEN 817 and 815. This is as desired. The zoom in level causes overlaps in the
//        //markers, it is not possible to zoom in using the system tests. So, when we "click" on 817, we are actually clicking
//        //on the 815 marker which is on top.
//        //due to overlap with other markers, we cannot click on 815 directly
//        marker2 = device.findObject(new UiSelector().descriptionContains("817"));
//        try {
//            marker2.click();
//        } catch (UiObjectNotFoundException e) {
//            e.printStackTrace();
//        }
//
//        android.os.SystemClock.sleep(1000);
//
//        textView = onView(
//                allOf(withId(R.id.info_card_title), withText("H-8 815"),
//                        childAtPosition(
//                                allOf(withId(R.id.infocardLinearLayout),
//                                        childAtPosition(
//                                                withId(R.id.bottom_card_frame),
//                                                0)),
//                                0),
//                        isDisplayed()));
//        textView.check(matches(withText("H-8 815")));
//
//        button = onView(
//                allOf(withId(R.id.directions),
//                        childAtPosition(
//                                childAtPosition(
//                                        withId(R.id.infocardLinearLayout),
//                                        1),
//                                0),
//                        isDisplayed()));
//        button.check(matches(isDisplayed()));
//
//        ViewInteraction view5 = onView(
//                allOf(childAtPosition(
//                        allOf(withContentDescription("Google Map"),
//                                childAtPosition(
//                                        IsInstanceOf.<View>instanceOf(android.widget.FrameLayout.class),
//                                        0)),
//                        24),
//                        isDisplayed()));
//
//        ViewInteraction view6 = onView(
//                allOf(childAtPosition(
//                        allOf(withContentDescription("Google Map"),
//                                childAtPosition(
//                                        IsInstanceOf.<View>instanceOf(android.widget.FrameLayout.class),
//                                        0)),
//                        16),
//                        isDisplayed()));
//
//        ViewInteraction view7 = onView(
//                allOf(childAtPosition(
//                        allOf(withContentDescription("Google Map"),
//                                childAtPosition(
//                                        IsInstanceOf.<View>instanceOf(android.widget.FrameLayout.class),
//                                        0)),
//                        36),
//                        isDisplayed()));
//
//        DataInteraction button6 = onData(anything())
//                .inAdapterView(allOf(withId(R.id.FloorPickerGv),
//                        childAtPosition(
//                                withId(R.id.locationFragment),
//                                3)))
//                .atPosition(1);
//        button6.perform(click());
//
//        DataInteraction button7 = onData(anything())
//                .inAdapterView(allOf(withId(R.id.FloorPickerGv),
//                        childAtPosition(
//                                withId(R.id.locationFragment),
//                                3)))
//                .atPosition(0);
//        button7.perform(click());
//    }
//
//    private static Matcher<View> childAtPosition(
//            final Matcher<View> parentMatcher, final int position) {
//
//        return new TypeSafeMatcher<View>() {
//            @Override
//            public void describeTo(Description description) {
//                description.appendText("Child at position " + position + " in parent ");
//                parentMatcher.describeTo(description);
//            }
//
//            @Override
//            public boolean matchesSafely(View view) {
//                ViewParent parent = view.getParent();
//                return parent instanceof ViewGroup && parentMatcher.matches(parent)
//                        && view.equals(((ViewGroup) parent).getChildAt(position));
//            }
//        };
//    }
//}

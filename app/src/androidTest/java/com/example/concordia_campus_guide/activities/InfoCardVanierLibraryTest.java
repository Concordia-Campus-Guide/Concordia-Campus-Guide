//package com.example.concordia_campus_guide.activities;
//
//
//import android.view.View;
//import android.view.ViewGroup;
//import android.view.ViewParent;
//
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
//import static androidx.test.espresso.Espresso.onView;
//import static androidx.test.espresso.action.ViewActions.click;
//import static androidx.test.espresso.assertion.ViewAssertions.matches;
//import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
//import static androidx.test.espresso.matcher.ViewMatchers.withId;
//import static androidx.test.espresso.matcher.ViewMatchers.withText;
//import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
//import static org.hamcrest.Matchers.allOf;
//
//@LargeTest
//@RunWith(AndroidJUnit4.class)
//public class InfoCardVanierLibraryTest {
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
//    public void infoCardVanierLibraryTest() {
//        EspressoHelpers.cancelNotificationIfExists();
//
//        ViewInteraction materialButton = onView(
//                allOf(withId(R.id.loyolaBtn), withText("Loyola"),
//                        childAtPosition(
//                                allOf(withId(R.id.campusSwitchBtnContainer),
//                                        childAtPosition(
//                                                withId(R.id.topMapContainer),
//                                                0)),
//                                1),
//                        isDisplayed()));
//        materialButton.perform(click());
//
//        android.os.SystemClock.sleep(1000);
//
//        UiObject marker = device.findObject(new UiSelector().descriptionContains("VL"));
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
//                        1),
//                        isDisplayed()));
//        button.check(matches(isDisplayed()));
//
//        ViewInteraction textView = onView(
//                allOf(withId(R.id.info_card_title), withText("Vanier Library Building"),
//                        childAtPosition(
//                                allOf(withId(R.id.infocardLinearLayout),
//                                        childAtPosition(
//                                                withId(R.id.bottom_card_frame),
//                                                0)),
//                                0),
//                        isDisplayed()));
//        textView.check(matches(withText("Vanier Library Building")));
//
//        ViewInteraction button2 = onView(
//                allOf(withId(R.id.directions),
//                        childAtPosition(
//                                childAtPosition(
//                                        withId(R.id.infocardLinearLayout),
//                                        1),
//                                0),
//                        isDisplayed()));
//        button2.check(matches(isDisplayed()));
//
//        ViewInteraction frameLayout = onView(
//                allOf(withId(R.id.bottom_card_frame),
//                        childAtPosition(
//                                allOf(withId(R.id.bottom_card_scroll_view),
//                                        childAtPosition(
//                                                withId(R.id.bottom_card_coordinator_layout),
//                                                1)),
//                                0),
//                        isDisplayed()));
//        frameLayout.perform(EspressoHelpers.hiddenClick());
//
//        android.os.SystemClock.sleep(1000);
//
//        ViewInteraction imageView = onView(
//                allOf(withId(R.id.building_image),
//                        childAtPosition(
//                                childAtPosition(
//                                        withId(R.id.infocardLinearLayout),
//                                        2),
//                                0),
//                        isDisplayed()));
//        imageView.check(matches(isDisplayed()));
//
//        ViewInteraction textView2 = onView(
//                allOf(withId(R.id.building_address), withText("7141 Sherbrooke West"),
//                        childAtPosition(
//                                childAtPosition(
//                                        IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class),
//                                        1),
//                                1),
//                        isDisplayed()));
//        textView2.check(matches(withText("7141 Sherbrooke West")));
//
//        ViewInteraction textView3 = onView(
//                allOf(withId(R.id.services_title), withText("Services"),
//                        isDisplayed()));
//        textView3.check(matches(withText("Services")));
//
//        ViewInteraction textView4 = onView(
//                allOf(withId(R.id.services_list), withText("Library"),
//                        isDisplayed()));
//        textView4.check(matches(withText("Library")));
//
//        ViewInteraction frameLayout2 = onView(
//                allOf(withId(R.id.bottom_card_frame),
//                        childAtPosition(
//                                allOf(withId(R.id.bottom_card_scroll_view),
//                                        childAtPosition(
//                                                withId(R.id.bottom_card_coordinator_layout),
//                                                1)),
//                                0),
//                        isDisplayed()));
//        frameLayout2.perform(click());
//
//        ViewInteraction textView5 = onView(
//                allOf(withId(R.id.info_card_title), withText("Explore"),
//                        childAtPosition(
//                                allOf(withId(R.id.poiLinearLayout),
//                                        childAtPosition(
//                                                IsInstanceOf.<View>instanceOf(android.widget.FrameLayout.class),
//                                                0)),
//                                0),
//                        isDisplayed()));
//        textView5.check(matches(withText("Explore")));
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

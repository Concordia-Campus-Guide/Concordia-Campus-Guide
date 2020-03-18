//package com.example.concordia_campus_guide;
//
//import android.view.View;
//import android.view.ViewGroup;
//import android.view.ViewParent;
//
//import com.example.concordia_campus_guide.Activities.MainActivity;
//
//import androidx.test.espresso.ViewInteraction;
//import androidx.test.rule.ActivityTestRule;
//import androidx.test.rule.GrantPermissionRule;
//import androidx.test.runner.AndroidJUnit4;
//import androidx.test.uiautomator.UiDevice;
//import androidx.test.uiautomator.UiObject;
//import androidx.test.uiautomator.UiObjectNotFoundException;
//import androidx.test.uiautomator.UiSelector;
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
//import static androidx.test.espresso.assertion.ViewAssertions.matches;
//import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
//import static androidx.test.espresso.matcher.ViewMatchers.withId;
//import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
//import static org.hamcrest.Matchers.allOf;
//
//@RunWith(AndroidJUnit4.class)
//public class InfoCardFragmentUITest {
//
//    private UiDevice device;
//
//    @Rule
//    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);
//
//    @Rule
//    public GrantPermissionRule mGrantPermissionRule =
//            GrantPermissionRule.grant(
//                    "android.permission.ACCESS_FINE_LOCATION",
//                    "android.permission.ACCESS_COARSE_LOCATION");
//
//    @Before
//    public void init() { device = UiDevice.getInstance(getInstrumentation()); }
//
//    /**
//     * This test validates that the info card pops up when a marker is clicked
//     * @throws UiObjectNotFoundException
//     * @throws InterruptedException
//     */
//    @Test
//    public void collapsedInfoCardPopsUpWhenMarkerIsClickedTest() throws UiObjectNotFoundException, InterruptedException {
//        UiObject marker = device.findObject(new UiSelector().descriptionContains("EV"));
//        marker.click();
//        Thread.sleep(1500);
//
//        ViewInteraction scrollView = onView(
//                allOf(withId(R.id.info_card),
//                        childAtPosition(
//                                allOf(withId(R.id.info_card_coordinator_layout),
//                                        childAtPosition(
//                                                IsInstanceOf.<View>instanceOf(android.view.ViewGroup.class),
//                                                1)),
//                                0),
//                        isDisplayed()));
//        scrollView.check(matches(isDisplayed()));
//    }
//
//    /**
//     * This test validates that there is the Directions button on the info card
//     * @throws UiObjectNotFoundException
//     * @throws InterruptedException
//     */
//    @Test
//    public void collapedInfoCardContainsDirectionsButtonTest() throws UiObjectNotFoundException, InterruptedException {
//        UiObject marker = device.findObject(new UiSelector().descriptionContains("EV"));
//        marker.click();
//        Thread.sleep(1500);
//
//        ViewInteraction directions = onView(
//                allOf(withId(R.id.directions),
//                        childAtPosition(
//                                childAtPosition(
//                                        IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class),
//                                        1),
//                                0),
//                        isDisplayed()));
//        directions.check(matches(isDisplayed()));
//    }
//
//    /**
//     * This test validates that there is the Indoor Map button on the info card
//     * @throws UiObjectNotFoundException
//     * @throws InterruptedException
//     */
//    @Test
//    public void collapsedInfoCardContainsIndoorMapButtonTest() throws UiObjectNotFoundException, InterruptedException {
//        UiObject marker = device.findObject(new UiSelector().descriptionContains("EV"));
//        marker.click();
//        Thread.sleep(1500);
//
//        ViewInteraction indoorMap = onView(
//                allOf(withId(R.id.indoor_map),
//                        childAtPosition(
//                                childAtPosition(
//                                        IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class),
//                                        1),
//                                1),
//                        isDisplayed()));
//        indoorMap.check(matches(isDisplayed()));
//    }
//
//    /**
//     * Helper method
//     */
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

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
//@RunWith(AndroidJUnit4.class)
//public class LocationFragmentUITest {
//    private UiDevice device;
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
//    public void init() {
//         device = UiDevice.getInstance(getInstrumentation());
//    }
//    /**
//     * The purpose of this test is to validate the existance of EV marker
//     * on the screen once opening the application.
//     * @throws UiObjectNotFoundException
//     */
//    @Test
//    public void testMarker() throws UiObjectNotFoundException {
//        UiObject marker = device.findObject(new UiSelector().descriptionContains("EV"));
//        marker.click();
//    }
//
//    /**
//     * The purpose of this test is to validate the existance of my location button.
//     * @throws UiObjectNotFoundException In case if it didn't find the my location button
//     */
//    @Test
//    public void CurrentLocationButtonTest() throws  UiObjectNotFoundException{
//        UiObject button = device.findObject(new UiSelector().descriptionContains("My Location"));
//        button.click();
//    }
//
//    /**
//     * The purpose of this test is to validate the existance of the switch button
//     * between the campuses.
//     */
//    @Test
//    public void SwitchButtonTest() {
//        ViewInteraction materialButton = onView(
//                allOf(withId(R.id.SGWBtn), withText("SGW"),
//                        childAtPosition(
//                                childAtPosition(
//                                        withId(R.id.locationFragment),
//                                        1),
//                                0),
//                        isDisplayed()));
//        materialButton.perform(click());
//
//        ViewInteraction button = onView(
//                allOf(withId(R.id.SGWBtn),
//                        childAtPosition(
//                                childAtPosition(
//                                        withId(R.id.locationFragment),
//                                        1),
//                                0),
//                        isDisplayed()));
//        button.check(matches(isDisplayed()));
//
//        ViewInteraction button2 = onView(
//                allOf(withId(R.id.loyolaBtn),
//                        childAtPosition(
//                                childAtPosition(
//                                        withId(R.id.locationFragment),
//                                        1),
//                                1),
//                        isDisplayed()));
//        button2.check(matches(isDisplayed()));
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
//
//    private void wait(int seconds) {
//        try {
//            Thread.sleep(1000*seconds);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//    }
//}
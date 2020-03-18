//package com.example.concordia_campus_guide;
//
//
//import android.view.View;
//import android.view.ViewGroup;
//import android.view.ViewParent;
//
//import org.hamcrest.Description;
//import org.hamcrest.Matcher;
//import org.hamcrest.TypeSafeMatcher;
//import org.hamcrest.core.IsInstanceOf;
//import org.junit.Rule;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//
//import androidx.test.espresso.ViewInteraction;
//import androidx.test.filters.LargeTest;
//import androidx.test.rule.GrantPermissionRule;
//import androidx.test.runner.AndroidJUnit4;
//import androidx.test.uiautomator.UiDevice;
//import androidx.test.uiautomator.UiObject;
//import androidx.test.uiautomator.UiObjectNotFoundException;
//import androidx.test.uiautomator.UiSelector;
//
//import static androidx.test.espresso.Espresso.onView;
//import static androidx.test.espresso.action.ViewActions.click;
//import static androidx.test.espresso.assertion.ViewAssertions.matches;
//import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
//import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
//import static androidx.test.espresso.matcher.ViewMatchers.withId;
//import static androidx.test.espresso.matcher.ViewMatchers.withText;
//import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
//import static org.hamcrest.Matchers.allOf;
//
//@LargeTest
//@RunWith(AndroidJUnit4.class)
//public class FloorPickerUITest {
//
//    @Rule
//    public GrantPermissionRule mGrantPermissionRule =
//            GrantPermissionRule.grant(
//                    "android.permission.ACCESS_FINE_LOCATION",
//                    "android.permission.ACCESS_COARSE_LOCATION");
//
//    @Test
//    public void floorPickerUITest() throws UiObjectNotFoundException {
//        UiDevice device = UiDevice.getInstance(getInstrumentation());
//        UiObject marker = device.findObject(new UiSelector().descriptionContains("MB"));
//        marker.click();
//
//        ViewInteraction gridView = onView(
//                allOf(withId(R.id.FloorPickerGv),
//                        childAtPosition(
//                                allOf(withId(R.id.locationFragment),
//                                        childAtPosition(
//                                                IsInstanceOf.<View>instanceOf(android.view.ViewGroup.class),
//                                                0)),
//                                2),
//                        isDisplayed()));
//        gridView.check(matches(isDisplayed()));
//    }
//
//    @Test
//    public void groundOverlayTest() throws UiObjectNotFoundException {
//        UiDevice device = UiDevice.getInstance(getInstrumentation());
//        UiObject marker = device.findObject(new UiSelector().descriptionContains("MB"));
//        marker.click();
//
//        ViewInteraction gridView = onView(
//                allOf(withId(R.id.FloorPickerGv),
//                        childAtPosition(
//                                allOf(withId(R.id.locationFragment),
//                                        childAtPosition(
//                                                IsInstanceOf.<View>instanceOf(android.view.ViewGroup.class),
//                                                0)),
//                                2),
//                        isDisplayed()));
//        gridView.check(matches(isDisplayed()));
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

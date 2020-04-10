package com.example.concordia_campus_guide.activities;


import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.espresso.DataInteraction;
import androidx.test.espresso.ViewInteraction;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.rule.GrantPermissionRule;
import androidx.test.runner.AndroidJUnit4;
import androidx.test.uiautomator.UiDevice;

import com.example.concordia_campus_guide.EspressoHelpers;
import com.example.concordia_campus_guide.R;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.hamcrest.core.IsInstanceOf;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.is;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class RoutesDirectionsHappyPathTest {

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
                    "android.permission.READ_CALENDAR");

    @Test
    public void routesDirectionsHappyPathTest() {
        //wait for gps
        android.os.SystemClock.sleep(4000);

        EspressoHelpers.cancelNotificationIfExists();

        ViewInteraction materialButtonX = onView(
                allOf(withId(android.R.id.button2), withText("Ignore"),
                        isDisplayed()));
        try{
            materialButtonX.perform(scrollTo(), click());
        }
        catch(Exception e){
            //ignore maybe the popup was cancelled by other test
        }

        ViewInteraction actionMenuItemView = onView(
                allOf(withId(R.id.search), withContentDescription("Icon"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.toolbar),
                                        2),
                                0),
                        isDisplayed()));
        actionMenuItemView.perform(click());

        android.os.SystemClock.sleep(1000);

        ViewInteraction map = onView(withContentDescription("Google Map"));

        ViewInteraction appCompatEditText = onView(
                allOf(withId(R.id.searchText),
                        childAtPosition(
                                allOf(withId(R.id.constraintLayout),
                                        childAtPosition(
                                                withClassName(is("androidx.constraintlayout.widget.ConstraintLayout")),
                                                0)),
                                0),
                        isDisplayed()));

        appCompatEditText.perform(click());

        appCompatEditText.perform(replaceText("ha"));

        android.os.SystemClock.sleep(1000);

        DataInteraction linearLayout = onData(anything())
                .inAdapterView(allOf(withId(R.id.searchResults),
                        childAtPosition(
                                withClassName(is("androidx.constraintlayout.widget.ConstraintLayout")),
                                4)))
                .atPosition(0);
        linearLayout.perform(click());

        android.os.SystemClock.sleep(1000);

        ViewInteraction textView = onView(
                allOf(withId(R.id.toText), withText("Henry F. Hall Building"),
                        isDisplayed()));
        textView.check(matches(isDisplayed()));

        ViewInteraction imageView = onView(
                allOf(withId(R.id.imageView2),
                        isDisplayed()));
        imageView.check(matches(isDisplayed()));

        ViewInteraction materialTextView = onView(
                allOf(withId(R.id.fromText),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.app_bar),
                                        0),
                                4),
                        isDisplayed()));
        materialTextView.check(matches(isDisplayed()));
        materialTextView.perform(click());

        ViewInteraction appCompatEditText2 = onView(
                allOf(withId(R.id.searchText),
                        childAtPosition(
                                allOf(withId(R.id.constraintLayout),
                                        childAtPosition(
                                                withClassName(is("androidx.constraintlayout.widget.ConstraintLayout")),
                                                0)),
                                0),
                        isDisplayed()));
        appCompatEditText2.perform(click());
        appCompatEditText2.perform(replaceText("van"), closeSoftKeyboard());

        DataInteraction linearLayout2 = onData(anything())
                .inAdapterView(allOf(withId(R.id.searchResults),
                        childAtPosition(
                                withClassName(is("androidx.constraintlayout.widget.ConstraintLayout")),
                                4)))
                .atPosition(1);
        linearLayout2.perform(click());

        ViewInteraction textView2 = onView(
                allOf(withId(R.id.fromText), withText("Vanier Library Building"),
                        isDisplayed()));
        textView2.check(matches(isDisplayed()));

        ViewInteraction textView3 = onView(
                allOf(withId(R.id.toText), withText("Henry F. Hall Building"),
                        isDisplayed()));
        textView3.check(matches(withText("Henry F. Hall Building")));

        ViewInteraction imageButton = onView(
                allOf(withId(R.id.filterButtonTransit),
                        childAtPosition(
                                allOf(withId(R.id.linearLayout),
                                        childAtPosition(
                                                IsInstanceOf.<View>instanceOf(android.view.ViewGroup.class),
                                                5)),
                                0),
                        isDisplayed()));
        imageButton.check(matches(isDisplayed()));

        ViewInteraction linearLayout3 = onView(
                allOf(withId(R.id.main),
                        childAtPosition(
                                allOf(withId(R.id.routeOverviewLayout),
                                        childAtPosition(
                                                withId(R.id.allRoutes),
                                                0)),
                                0),
                        isDisplayed()));
        linearLayout3.check(matches(isDisplayed()));

        DataInteraction linearLayout4 = onData(anything())
                .inAdapterView(allOf(withId(R.id.allRoutes),
                        childAtPosition(
                                withClassName(is("androidx.coordinatorlayout.widget.CoordinatorLayout")),
                                1)))
                .atPosition(0);
        linearLayout4.perform(click());

        android.os.SystemClock.sleep(2000);

        ViewInteraction linearLayout5 = onView(
                    allOf(withId(R.id.path_info_card_frame),
                        isDisplayed()));
        linearLayout5.check(matches(isDisplayed()));

        ViewInteraction textView4 = onView(
                allOf(withId(R.id.path_fromText), withText("Vanier Library Building"),
                        isDisplayed()));
        textView4.check(matches(withText("Vanier Library Building")));

        ViewInteraction textView5 = onView(
                allOf(withId(R.id.path_toText), withText("Henry F. Hall Building"),
                        isDisplayed()));
        textView5.check(matches(withText("Henry F. Hall Building")));

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

        android.os.SystemClock.sleep(1000);

        device.pressBack();
        android.os.SystemClock.sleep(1000);

        device.pressBack();

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

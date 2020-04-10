package com.example.concordia_campus_guide.activities;


import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.espresso.ViewInteraction;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.rule.GrantPermissionRule;
import androidx.test.runner.AndroidJUnit4;

import com.example.concordia_campus_guide.EspressoHelpers;
import com.example.concordia_campus_guide.R;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.hamcrest.core.IsInstanceOf;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class ExplorePOICardTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Rule
    public GrantPermissionRule mGrantPermissionRule =
            GrantPermissionRule.grant(
                    "android.permission.ACCESS_FINE_LOCATION",
                    "android.permission.ACCESS_COARSE_LOCATION",
                    "android.permission.READ_CALENDAR");

    @Test
    public void explorePOICardTest() {
        EspressoHelpers.cancelNotificationIfExists();

        android.os.SystemClock.sleep(500);

        ViewInteraction textView3 = onView(
                allOf(EspressoHelpers.getElementFromMatchAtPosition(allOf(withId(R.id.info_card_title), withText("Explore")), 0),
                        isDisplayed()));
        textView3.check(matches(withText("Explore")));

        ViewInteraction frameLayout = onView(
                allOf(withId(R.id.explore_bottom_card_frame),
                        childAtPosition(
                                allOf(withId(R.id.explore_bottom_card_scroll_view),
                                        childAtPosition(
                                                withId(R.id.bottom_card_coordinator_layout),
                                                0)),
                                0),
                        isDisplayed()));
        frameLayout.perform(EspressoHelpers.hiddenClick());
        android.os.SystemClock.sleep(500);

        ViewInteraction imageView = onView(
                allOf(EspressoHelpers.getElementFromMatchAtPosition(allOf(withId(R.id.serviceIv)), 0),
                        isDisplayed()));

        imageView.check(matches(isDisplayed()));

        ViewInteraction textView = onView(
                allOf(EspressoHelpers.getElementFromMatchAtPosition(allOf(withId(R.id.info_card_title), withText("Explore")), 0),
                        isDisplayed()));
        textView.check(matches(withText("Explore")));

        ViewInteraction tabView = onView(
                allOf(childAtPosition(
                        childAtPosition(
                                withId(R.id.dotsTab),
                                0),
                        1),
                        isDisplayed()));
        tabView.perform(click());

        ViewInteraction imageView2 = onView(
                allOf(EspressoHelpers.getElementFromMatchAtPosition(allOf(withId(R.id.serviceIv)), 0),
                        isDisplayed()));

        imageView2.check(matches(isDisplayed()));

        ViewInteraction frameLayout2 = onView(
                allOf(withId(R.id.explore_bottom_card_frame),
                        childAtPosition(
                                allOf(withId(R.id.explore_bottom_card_scroll_view),
                                        childAtPosition(
                                                withId(R.id.bottom_card_coordinator_layout),
                                                0)),
                                0),
                        isDisplayed()));
        frameLayout2.perform(EspressoHelpers.hiddenClick());
        android.os.SystemClock.sleep(500);

        ViewInteraction textView2 = onView(
                allOf(withId(R.id.info_card_title), withText("Explore"),
                        childAtPosition(
                                allOf(withId(R.id.poiLinearLayout),
                                        childAtPosition(
                                                IsInstanceOf.<View>instanceOf(android.widget.FrameLayout.class),
                                                0)),
                                0),
                        isDisplayed()));
        textView2.check(matches(withText("Explore")));
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

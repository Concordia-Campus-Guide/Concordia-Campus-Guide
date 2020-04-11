package com.example.concordia_campus_guide.activities;


import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import com.example.concordia_campus_guide.R;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.hamcrest.core.IsInstanceOf;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.test.espresso.ViewAssertion;
import androidx.test.espresso.ViewInteraction;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.rule.GrantPermissionRule;
import androidx.test.runner.AndroidJUnit4;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.pressBack;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class SideMenuFrenchTranslationTest {

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
    public void sideMenuFrenchTranslationTest() {

        onView(allOf(withId(R.id.info_card_title), withText(R.string.point_of_interest_explore))).perform(click());

        onView(withIndex(withId(R.id.serviceTv), 0)).check(matches(withText("Elevator")));
        onView(withIndex(withId(R.id.serviceTv), 1)).check(matches(withText("Lounges")));
        onView(withIndex(withId(R.id.serviceTv), 2)).check(matches(withText("Stairs")));
        onView(withIndex(withId(R.id.serviceTv), 3)).check(matches(withText("Washroom")));

        onView(withContentDescription(R.string.navigation_drawer_open)).perform(click());

        onView(withText(R.string.calendar)).check(matches(isDisplayed()));
        onView(withText(R.string.french)).check(matches(isDisplayed()));
        onView(withText(R.string.staff)).check(matches(isDisplayed()));
        onView(withText(R.string.accessibility)).check(matches(isDisplayed()));


        ViewInteraction switch_ = onView(
                allOf(withId(R.id.nav_translate),
                        isDisplayed()));
        switch_.perform(click());

        pressBack();

        onView(withIndex(withId(R.id.serviceTv), 0)).check(matches(withText("Ascenseur")));
        onView(withIndex(withId(R.id.serviceTv), 1)).check(matches(withText("Salons")));
        onView(withIndex(withId(R.id.serviceTv), 2)).check(matches(withText("Escalier")));
        onView(withIndex(withId(R.id.serviceTv), 3)).check(matches(withText("Toilette")));

        onView(withContentDescription(R.string.navigation_drawer_open)).perform(click());

        ViewInteraction switch_2 = onView(
                allOf(withId(R.id.nav_translate),
                        isDisplayed()));
        switch_2.perform(click());

        pressBack();
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

    public static Matcher<View> withIndex(final Matcher<View> matcher, final int index) {
        return new TypeSafeMatcher<View>() {
            int currentIndex = 0;

            @Override
            public void describeTo(Description description) {
                description.appendText("with index: ");
                description.appendValue(index);
                matcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                return matcher.matches(view) && currentIndex++ == index;
            }
        };
    }
}

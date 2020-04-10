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

import androidx.test.espresso.ViewInteraction;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.rule.GrantPermissionRule;
import androidx.test.runner.AndroidJUnit4;

import static androidx.test.espresso.Espresso.onView;
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
public class SideMenuAndFrenchTest {

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
    public void sideMenuAndFrenchTest() {
        ViewInteraction appCompatImageButton = onView(
                allOf(withContentDescription("Open navigation drawer"),
                        childAtPosition(
                                allOf(withId(R.id.toolbar),
                                        childAtPosition(
                                                withId(R.id.appBarLayout),
                                                0)),
                                1),
                        isDisplayed()));
        appCompatImageButton.perform(click());

        ViewInteraction navigationMenuItemView = onView(
                allOf(childAtPosition(
                        allOf(withId(R.id.design_navigation_view),
                                childAtPosition(
                                        withId(R.id.side_nav_view),
                                        0)),
                        1),
                        isDisplayed()));
        navigationMenuItemView.perform(click());

        ViewInteraction switch_ = onView(
                allOf(withId(R.id.nav_translate),
                        childAtPosition(
                                allOf(withId(R.id.design_menu_item_action_area),
                                        childAtPosition(
                                                withClassName(is("com.google.android.material.internal.NavigationMenuItemView")),
                                                1)),
                                0),
                        isDisplayed()));
        switch_.perform(click());

        ViewInteraction switch_2 = onView(
                allOf(withId(R.id.nav_accessibility),
                        childAtPosition(
                                allOf(withId(R.id.design_menu_item_action_area),
                                        childAtPosition(
                                                withClassName(is("com.google.android.material.internal.NavigationMenuItemView")),
                                                1)),
                                0),
                        isDisplayed()));
        switch_2.perform(click());

        ViewInteraction switch_3 = onView(
                allOf(withId(R.id.nav_staff),
                        childAtPosition(
                                allOf(withId(R.id.design_menu_item_action_area),
                                        childAtPosition(
                                                withClassName(is("com.google.android.material.internal.NavigationMenuItemView")),
                                                1)),
                                0),
                        isDisplayed()));
        switch_3.perform(click());

        ViewInteraction frameLayout = onView(
                allOf(withId(R.id.explore_bottom_card_frame),
                        childAtPosition(
                                allOf(withId(R.id.explore_bottom_card_scroll_view),
                                        childAtPosition(
                                                withId(R.id.bottom_card_coordinator_layout),
                                                0)),
                                0),
                        isDisplayed()));
        frameLayout.perform(click());

        ViewInteraction textView = onView(
                allOf(withId(R.id.info_card_title), withText("Explorer"),
                        childAtPosition(
                                allOf(withId(R.id.poiLinearLayout),
                                        childAtPosition(
                                                IsInstanceOf.<View>instanceOf(android.widget.FrameLayout.class),
                                                0)),
                                0),
                        isDisplayed()));
        textView.check(matches(withText("Explorer")));

        ViewInteraction textView2 = onView(
                allOf(withId(R.id.serviceTv), withText("Ascenseur"),
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.<View>instanceOf(android.view.ViewGroup.class),
                                        0),
                                1),
                        isDisplayed()));
        textView2.check(matches(withText("Ascenseur")));

        ViewInteraction textView3 = onView(
                allOf(withId(R.id.serviceTv), withText("Salons"),
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.<View>instanceOf(android.view.ViewGroup.class),
                                        0),
                                1),
                        isDisplayed()));
        textView3.check(matches(withText("Salons")));

        ViewInteraction textView4 = onView(
                allOf(withId(R.id.serviceTv), withText("Escalier"),
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.<View>instanceOf(android.view.ViewGroup.class),
                                        0),
                                1),
                        isDisplayed()));
        textView4.check(matches(withText("Escalier")));
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

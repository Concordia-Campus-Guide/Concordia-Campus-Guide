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


        pressBack();

        ViewInteraction frameLayout = onView(
                allOf(withId(R.id.info_card_title),
                        isDisplayed()));
        frameLayout.perform(click());



        ViewInteraction textView = onView(
                allOf(withId(R.id.serviceTv), withText("Elevator"),
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.<View>instanceOf(android.view.ViewGroup.class),
                                        0),
                                1),
                        isDisplayed()));
        textView.check(matches(withText("Elevator")));

        ViewInteraction textView2 = onView(
                allOf(withId(R.id.serviceTv), withText("Lounges"),
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.<View>instanceOf(android.view.ViewGroup.class),
                                        0),
                                1),
                        isDisplayed()));
        textView2.check(matches(withText("Lounges")));

        ViewInteraction textView3 = onView(
                allOf(withId(R.id.serviceTv), withText("Stairs"),
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.<View>instanceOf(android.view.ViewGroup.class),
                                        0),
                                1),
                        isDisplayed()));
        textView3.check(matches(withText("Stairs")));

        ViewInteraction textView4 = onView(
                allOf(withId(R.id.serviceTv), withText("Washroom"),
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.<View>instanceOf(android.view.ViewGroup.class),
                                        0),
                                1),
                        isDisplayed()));
        textView4.check(matches(withText("Washroom")));

        ViewInteraction frameLayout2 = onView(
                allOf(withId(R.id.explore_bottom_card_frame),
                        childAtPosition(
                                allOf(withId(R.id.explore_bottom_card_scroll_view),
                                        childAtPosition(
                                                withId(R.id.bottom_card_coordinator_layout),
                                                0)),
                                0),
                        isDisplayed()));
        frameLayout2.perform(click());

        ViewInteraction appCompatImageButton2 = onView(
                allOf(withContentDescription("Open navigation drawer"),
                        childAtPosition(
                                allOf(withId(R.id.toolbar),
                                        childAtPosition(
                                                withId(R.id.appBarLayout),
                                                0)),
                                1),
                        isDisplayed()));
        appCompatImageButton2.perform(click());

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

        pressBack();

        ViewInteraction frameLayout3 = onView(
                allOf(withId(R.id.info_card_title),
                        isDisplayed()));

        ViewInteraction textView5 = onView(
                allOf(withId(R.id.serviceTv), withText("Ascenseur"),
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.<View>instanceOf(android.view.ViewGroup.class),
                                        0),
                                1),
                        isDisplayed()));
        textView5.check(matches(withText("Ascenseur")));

        ViewInteraction textView6 = onView(
                allOf(withId(R.id.serviceTv), withText("Salons"),
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.<View>instanceOf(android.view.ViewGroup.class),
                                        0),
                                1),
                        isDisplayed()));
        textView6.check(matches(withText("Salons")));

        ViewInteraction textView7 = onView(
                allOf(withId(R.id.serviceTv), withText("Escalier"),
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.<View>instanceOf(android.view.ViewGroup.class),
                                        0),
                                1),
                        isDisplayed()));
        textView7.check(matches(withText("Escalier")));

        ViewInteraction textView8 = onView(
                allOf(withId(R.id.serviceTv), withText("Toilette"),
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.<View>instanceOf(android.view.ViewGroup.class),
                                        0),
                                1),
                        isDisplayed()));
        textView8.check(matches(withText("Toilette")));
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

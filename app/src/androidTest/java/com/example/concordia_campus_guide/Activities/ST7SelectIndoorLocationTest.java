package com.example.concordia_campus_guide.Activities;


import android.content.ComponentName;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.espresso.DataInteraction;
import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.filters.LargeTest;
import androidx.test.rule.GrantPermissionRule;
import androidx.test.runner.AndroidJUnit4;
import androidx.test.uiautomator.UiDevice;

import com.example.concordia_campus_guide.R;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
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
public class ST7SelectIndoorLocationTest {

    private UiDevice device;

    @Before
    public void init() { device = UiDevice.getInstance(getInstrumentation()); }

    @Rule
    public IntentsTestRule<MainActivity> mActivityTestRule = new IntentsTestRule<>(MainActivity.class);

    @Rule
    public GrantPermissionRule mGrantPermissionRule =
            GrantPermissionRule.grant(
                    "android.permission.ACCESS_FINE_LOCATION",
                    "android.permission.ACCESS_COARSE_LOCATION",
                    "android.permission.READ_CALENDAR");

    @Test
    public void routesActivityOptionsTest() {
        ViewInteraction textView = onView(
                allOf(withId(R.id.search), withContentDescription("Icon"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.toolbar),
                                        1),
                                0),
                        isDisplayed()));
        textView.check(matches(withText("")));

        ViewInteraction actionMenuItemView = onView(
                allOf(withId(R.id.search), withContentDescription("Icon"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.toolbar),
                                        1),
                                0),
                        isDisplayed()));
        actionMenuItemView.perform(click());

        android.os.SystemClock.sleep(2000);

        intended(hasComponent(new ComponentName(getInstrumentation().getContext(), SearchActivity.class)));

        android.os.SystemClock.sleep(1000);

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
        android.os.SystemClock.sleep(1000);

        appCompatEditText.perform(replaceText("8"), closeSoftKeyboard());

        ViewInteraction textView6 = onView(
                allOf(withId(R.id.textView), withText("H-8 819"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.searchResults),
                                        1),
                                1),
                        isDisplayed()));
        textView6.check(matches(withText("H-8 819")));

        DataInteraction linearLayout = onData(anything())
                .inAdapterView(allOf(withId(R.id.searchResults),
                        childAtPosition(
                                withClassName(is("androidx.constraintlayout.widget.ConstraintLayout")),
                                4)))
                .atPosition(1);
        linearLayout.perform(click());

        android.os.SystemClock.sleep(2000);

        ViewInteraction textView2 = onView(
                allOf(withId(R.id.toText), withText("H-8 819"),
                        isDisplayed()));
        textView2.check(matches(isDisplayed()));
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

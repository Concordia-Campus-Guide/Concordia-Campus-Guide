package com.example.concordia_campus_guide.Activities;


import android.content.ComponentName;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.filters.LargeTest;
import androidx.test.rule.GrantPermissionRule;
import androidx.test.runner.AndroidJUnit4;

import com.example.concordia_campus_guide.EspressoHelpers;
import com.example.concordia_campus_guide.R;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

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
import static org.hamcrest.Matchers.is;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class SearchActivityTest {

    @Rule
    public IntentsTestRule<MainActivity> mActivityTestRule = new IntentsTestRule<>(MainActivity.class);

    @Rule
    public GrantPermissionRule mGrantPermissionRule =
            GrantPermissionRule.grant(
                    "android.permission.ACCESS_FINE_LOCATION",
                    "android.permission.ACCESS_COARSE_LOCATION",
                    "android.permission.READ_CALENDAR");

    @Test
    public void searchActivityTest() {
        EspressoHelpers.cancelNotificationIfExists();

        ViewInteraction textView = onView(
                allOf(withId(R.id.search), withContentDescription("Icon"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.toolbar),
                                        2),
                                0),
                        isDisplayed()));
        textView.check(matches(withText("")));

        ViewInteraction actionMenuItemView = onView(
                allOf(withId(R.id.search), withContentDescription("Icon"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.toolbar),
                                        2),
                                0),
                        isDisplayed()));
        actionMenuItemView.perform(click());

        android.os.SystemClock.sleep(2000);

        intended(hasComponent(new ComponentName(getInstrumentation().getContext(), SearchActivity.class)));

        android.os.SystemClock.sleep(1000);

        ViewInteraction textView2 = onView(
                allOf(withId(R.id.next_class_text),
                        isDisplayed()));
        textView2.check(matches(isDisplayed()));

        ViewInteraction editText = onView(
                allOf(withId(R.id.searchText),
                        isDisplayed()));

        editText.check(matches(isDisplayed()));

        ViewInteraction textView3 = onView(
                allOf(withId(R.id.next_class_title), withText("Next class"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                2),
                        isDisplayed()));
        textView3.check(matches(isDisplayed()));

        editText.perform(click());

        android.os.SystemClock.sleep(1000);

        ViewInteraction textView4 = onView(
                allOf(withId(R.id.textView), withText("Administration Building"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.searchResults),
                                        0),
                                1),
                        isDisplayed()));
        textView4.check(matches(isDisplayed()));

        ViewInteraction textView5 = onView(
                allOf(withId(R.id.textView), withText("B Annex"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.searchResults),
                                        1),
                                1),
                        isDisplayed()));
        textView5.check(matches(withText("B Annex")));

        ViewInteraction appCompatEditText = onView(
                allOf(withId(R.id.searchText),
                        childAtPosition(
                                allOf(withId(R.id.constraintLayout),
                                        childAtPosition(
                                                withClassName(is("androidx.constraintlayout.widget.ConstraintLayout")),
                                                0)),
                                0),
                        isDisplayed()));
        appCompatEditText.perform(replaceText("8"), closeSoftKeyboard());

        ViewInteraction textView6 = onView(
                allOf(withId(R.id.textView), withText("H-8"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.searchResults),
                                        0),
                                1),
                        isDisplayed()));
        textView6.check(matches(withText("H-8")));

        ViewInteraction imageView = onView(
                allOf(withId(R.id.place_icon),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.searchResults),
                                        0),
                                0),
                        isDisplayed()));
        imageView.check(matches(isDisplayed()));

        ViewInteraction textView7 = onView(
                allOf(withId(R.id.textView), withText("H-8 819"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.searchResults),
                                        1),
                                1),
                        isDisplayed()));
        textView7.check(matches(withText("H-8 819")));

        ViewInteraction imageView2 = onView(
                allOf(withId(R.id.place_icon),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.searchResults),
                                        1),
                                0),
                        isDisplayed()));
        imageView2.check(matches(isDisplayed()));

        ViewInteraction appCompatImageButton = onView(
                allOf(withId(R.id.back),
                        childAtPosition(
                                allOf(withId(R.id.constraintLayout),
                                        childAtPosition(
                                                withClassName(is("androidx.constraintlayout.widget.ConstraintLayout")),
                                                0)),
                                1),
                        isDisplayed()));
        appCompatImageButton.perform(click());

        android.os.SystemClock.sleep(2000);
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

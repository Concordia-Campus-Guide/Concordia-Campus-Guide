package com.example.concordia_campus_guide.Activities;


import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.espresso.ViewInteraction;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.rule.GrantPermissionRule;
import androidx.test.runner.AndroidJUnit4;

import com.example.concordia_campus_guide.R;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.scrollTo;
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
public class CalendarSuggestionTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Rule
    public GrantPermissionRule mGrantPermissionRule =
            GrantPermissionRule.grant(
                    "android.permission.ACCESS_FINE_LOCATION",
                    "android.permission.ACCESS_COARSE_LOCATION",
                    "android.permission.READ_CALENDAR");

    @Test
    public void calendarSuggestionTest() {
        android.os.SystemClock.sleep(5000);

        ViewInteraction materialButton = onView(
                allOf(withId(android.R.id.button2), withText("Ignore"),
                    isDisplayed()));
        try{
            materialButton.perform(scrollTo(), click());
        }
        catch(Exception e){
            //ignore maybe the popup was cancelled by other test
        }

        ViewInteraction actionMenuItemView = onView(
                allOf(withId(R.id.search), withContentDescription("Icon"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.toolbar),
                                        1),
                                0),
                        isDisplayed()));
        actionMenuItemView.perform(click());

        android.os.SystemClock.sleep(1000);

        ViewInteraction textView = onView(
                allOf(withId(R.id.next_class_text),
                        isDisplayed()));
        textView.check(matches(isDisplayed()));

        ViewInteraction view = onView(
                allOf(withId(R.id.view_container),
                        childAtPosition(
                                allOf(withId(R.id.next_class_row),
                                        childAtPosition(
                                                withClassName(is("androidx.constraintlayout.widget.ConstraintLayout")),
                                                3)),
                                0),
                        isDisplayed()));
        view.perform(click());

        android.os.SystemClock.sleep(2000);

        ViewInteraction textView2 = onView(
                allOf(withId(R.id.toText), withText("H-9 937"),
                        isDisplayed()));
        textView2.check(matches(withText("H-9 937")));
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

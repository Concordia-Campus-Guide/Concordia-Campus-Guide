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

        verifyEnglishExploreCard();

        openSideMenuAndVerify();

        onView(allOf(withId(R.id.nav_translate), isDisplayed())).perform(click());

        pressBack();

        verifyFrenchExploreCard();

        // go back to original

        onView(withContentDescription(R.string.navigation_drawer_open)).perform(click());

        onView(allOf(withId(R.id.nav_translate), isDisplayed())).perform(click());

        pressBack();
    }

    private void verifyFrenchExploreCard() {
        String[] frenchServicesTextExpected = {"Ascenseur", "Salons", "Escalier", "Toilette"};
        verifyExploreCard(frenchServicesTextExpected);
    }

    private void verifyEnglishExploreCard() {
        onView(allOf(withId(R.id.info_card_title), withText(R.string.point_of_interest_explore))).perform(click());

        String[] englishServicesTextExpected = {"Elevator", "Lounges", "Stairs", "Washroom"};
        verifyExploreCard(englishServicesTextExpected);
    }

    private void openSideMenuAndVerify() {
        onView(withContentDescription(R.string.navigation_drawer_open)).perform(click());

        onView(withText(R.string.calendar)).check(matches(isDisplayed()));
        onView(withText(R.string.french)).check(matches(isDisplayed()));
        onView(withText(R.string.staff)).check(matches(isDisplayed()));
        onView(withText(R.string.accessibility)).check(matches(isDisplayed()));
    }

    public void verifyExploreCard(String[] servicesTextExpected){
        for(int i =0; i < servicesTextExpected.length; i++){
            onView(withIndex(withId(R.id.serviceTv), i)).check(matches(withText(servicesTextExpected[i])));
        }
    }

    // very important, without this it will always fail with gridviews elemnent as they share same id
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

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
import androidx.test.uiautomator.UiDevice;
import androidx.test.uiautomator.UiObject;
import androidx.test.uiautomator.UiObjectNotFoundException;
import androidx.test.uiautomator.UiSelector;

import com.example.concordia_campus_guide.EspressoHelpers;
import com.example.concordia_campus_guide.R;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class RoutesActivityOptionsTest {

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
        //wait for gps
        android.os.SystemClock.sleep(4000);

        ViewInteraction materialButtonX = onView(
                allOf(withId(android.R.id.button2), withText("Ignore"),
                        isDisplayed()));
        try{
            materialButtonX.perform(scrollTo(), click());
        }
        catch(Exception e){
            //ignore maybe the popup was cancelled by other test
        }

        ViewInteraction materialButton = onView(
                allOf(withId(R.id.SGWBtn), withText("SGW"),
                        childAtPosition(
                                allOf(withId(R.id.campusSwitchBtnContainer),
                                        childAtPosition(
                                                withId(R.id.topMapContainer),
                                                0)),
                                0),
                        isDisplayed()));
        materialButton.perform(click());

        android.os.SystemClock.sleep(1000);

        UiObject marker = device.findObject(new UiSelector().descriptionContains("H"));
        try {
            marker.click();
        } catch (UiObjectNotFoundException e) {
            e.printStackTrace();
        }

        android.os.SystemClock.sleep(1000);

        ViewInteraction materialButton2 = onView(
                allOf(withId(R.id.directions), withText("Directions"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.infocardLinearLayout),
                                        1),
                                0),
                        isDisplayed()));
        materialButton2.perform(click());

        android.os.SystemClock.sleep(1000);

        ViewInteraction textView = onView(
                allOf(withId(R.id.toText), withText("Henry F. Hall Building"),
                        isDisplayed()));
        textView.check(matches(isDisplayed()));

        ViewInteraction viewGroup = onView(
                allOf(EspressoHelpers.getElementFromMatchAtPosition(
                        allOf(withId(R.id.details)), 0),
                        isDisplayed()));
        viewGroup.check(matches(isDisplayed()));

        ViewInteraction imageView = onView(
                allOf(EspressoHelpers.getElementFromMatchAtPosition(
                        allOf(withId(R.id.mainTransportType)), 0),
                        isDisplayed()));
        imageView.check(matches(isDisplayed()));

        ViewInteraction textView2 = onView(
                allOf(withId(R.id.duration), withText("5 mins"),
                        childAtPosition(
                                allOf(withId(R.id.main),
                                        childAtPosition(
                                                withId(R.id.routeOverviewLayout),
                                                0)),
                                2),
                        isDisplayed()));
        textView2.check(matches(isDisplayed()));

        ViewInteraction viewGroup2 = onView(
                allOf(EspressoHelpers.getElementFromMatchAtPosition(
                        allOf(withId(R.id.details)), 0),
                        isDisplayed()));
        viewGroup2.check(matches(isDisplayed()));

        ViewInteraction imageView2 = onView(
                allOf(EspressoHelpers.getElementFromMatchAtPosition(allOf(withId(R.id.mainTransportType)), 0),
                        isDisplayed()));

        imageView2.check(matches(isDisplayed()));

        ViewInteraction appCompatImageButton = onView(
                allOf(withId(R.id.filterButtonShuttle),
                        childAtPosition(
                                allOf(withId(R.id.linearLayout),
                                        childAtPosition(
                                                withClassName(is("androidx.constraintlayout.widget.ConstraintLayout")),
                                                5)),
                                1),
                        isDisplayed()));
        appCompatImageButton.perform(click());
        android.os.SystemClock.sleep(1000);

        ViewInteraction viewGroup3 = onView(
                allOf(EspressoHelpers.getElementFromMatchAtPosition(
                        allOf(withId(R.id.details)), 0),
                        isDisplayed()));
        viewGroup3.check(matches(isDisplayed()));

        ViewInteraction imageView4 = onView(
                allOf(EspressoHelpers.getElementFromMatchAtPosition(
                        allOf(withId(R.id.mainTransportType)), 0),
                        isDisplayed()));
        imageView4.check(matches(isDisplayed()));

        ViewInteraction appCompatImageButton2 = onView(
                allOf(withId(R.id.filterButtonWalk),
                        childAtPosition(
                                allOf(withId(R.id.linearLayout),
                                        childAtPosition(
                                                withClassName(is("androidx.constraintlayout.widget.ConstraintLayout")),
                                                5)),
                                2),
                        isDisplayed()));
        appCompatImageButton2.perform(click());
        android.os.SystemClock.sleep(1000);

        ViewInteraction textView4 = onView(
                allOf(withText("Rue Guy and Boulevard de Maisonneuve O"),
                        childAtPosition(
                                allOf(withId(R.id.details),
                                        childAtPosition(
                                                withId(R.id.main),
                                                1)),
                                0),
                        isDisplayed()));
        textView4.check(matches(isDisplayed()));

        ViewInteraction textView5 = onView(
                allOf(withText("Rue Guy and Boulevard de Maisonneuve O"),
                        childAtPosition(
                                allOf(withId(R.id.details),
                                        childAtPosition(
                                                withId(R.id.main),
                                                1)),
                                0),
                        isDisplayed()));
        textView5.check(matches(isDisplayed()));

        ViewInteraction textView6 = onView(
                allOf(EspressoHelpers.getElementFromMatchAtPosition(allOf(withText("5 mins"),
                        isDisplayed()), 0)));

        textView6.check(matches(isDisplayed()));

        ViewInteraction appCompatImageButton3 = onView(
                allOf(withId(R.id.filterButtonCar),
                        childAtPosition(
                                allOf(withId(R.id.linearLayout),
                                        childAtPosition(
                                                withClassName(is("androidx.constraintlayout.widget.ConstraintLayout")),
                                                5)),
                                3),
                        isDisplayed()));
        appCompatImageButton3.perform(click());

        android.os.SystemClock.sleep(1000);

        ViewInteraction imageView6 = onView(
                allOf(EspressoHelpers.getElementFromMatchAtPosition(allOf(withId(R.id.mainTransportType)), 0),
                        isDisplayed()));
        imageView6.check(matches(isDisplayed()));

        ViewInteraction textView8 = onView(
                allOf(withText("Rue Sainte-Catherine O and Rue Bishop"),
                        childAtPosition(
                                allOf(withId(R.id.details),
                                        childAtPosition(
                                                withId(R.id.main),
                                                1)),
                                0),
                        isDisplayed()));
        textView8.check(matches(isDisplayed()));

        ViewInteraction textView9 = onView(
                allOf(withId(R.id.duration), withText("4 mins"),
                        childAtPosition(
                                allOf(withId(R.id.main),
                                        childAtPosition(
                                                withId(R.id.routeOverviewLayout),
                                                0)),
                                2),
                        isDisplayed()));

        textView9.check(matches(isDisplayed()));

        android.os.SystemClock.sleep(1000);

        ViewInteraction appCompatImageButtonX = onView(
                allOf(withId(R.id.routesPageBackButton),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.app_bar),
                                        0),
                                2),
                        isDisplayed()));
        appCompatImageButtonX.perform(click());

        android.os.SystemClock.sleep(1000);

        device.pressBack();

        intended(hasComponent(new ComponentName(getInstrumentation().getContext(), MainActivity.class)));

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

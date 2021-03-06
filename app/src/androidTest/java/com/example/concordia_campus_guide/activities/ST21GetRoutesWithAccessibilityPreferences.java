package com.example.concordia_campus_guide.activities;


import android.content.ComponentName;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.espresso.DataInteraction;
import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.filters.LargeTest;
import androidx.test.rule.GrantPermissionRule;
import androidx.test.runner.AndroidJUnit4;
import androidx.test.runner.screenshot.Screenshot;
import androidx.test.uiautomator.UiDevice;
import androidx.test.uiautomator.UiObject;
import androidx.test.uiautomator.UiObjectNotFoundException;
import androidx.test.uiautomator.UiSelector;

import com.example.concordia_campus_guide.EspressoHelpers;
import com.example.concordia_campus_guide.R;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Assert;
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
public class ST21GetRoutesWithAccessibilityPreferences {

    private UiDevice device;
    private static Bitmap ScreenshotOfMap8thAccessibility;
    private static Bitmap ScreenshotOfMap9thAccessibility;
    private static Bitmap ScreenshotOfMap8thNoAccessibility;
    private static Bitmap ScreenshotOfMap9thNoAccessibility;

    @Before
    public void init() { device = UiDevice.getInstance(getInstrumentation()); }

    @Rule
    public IntentsTestRule<MainActivity> mActivityTestRule = new IntentsTestRule<>(MainActivity.class);

    @Rule
    public GrantPermissionRule mGrantPermissionRule =
            GrantPermissionRule.grant(
                    "android.permission.ACCESS_FINE_LOCATION",
                    "android.permission.ACCESS_COARSE_LOCATION",
                    "android.permission.WRITE_CALENDAR",
                    "android.permission.READ_CALENDAR");

    @Test
    public void routesActivityOptionsTest() {
        EspressoHelpers.cancelNotificationIfExists();
        ScreenshotOfMap8thAccessibility = EspressoHelpers.importScreenshot("screenshot_testing_screenshots/ST21ScreenshotAccessibility8thFloor.txt");
        ScreenshotOfMap9thAccessibility = EspressoHelpers.importScreenshot("screenshot_testing_screenshots/ST21ScreenshotAccessibility9thFloor.txt");
        ScreenshotOfMap8thNoAccessibility = EspressoHelpers.importScreenshot("screenshot_testing_screenshots/ST21ScreenshotNoAccessibility8thFloor.txt");
        ScreenshotOfMap9thNoAccessibility = EspressoHelpers.importScreenshot("screenshot_testing_screenshots/ST21ScreenshotNoAccessibility9thFloor.txt");

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

        appCompatEditText.perform(replaceText("855"), closeSoftKeyboard());

        ViewInteraction textView6 = onView(
                allOf(withId(R.id.textView), withText("H-8 855"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.searchResults),
                                        0),
                                1),
                        isDisplayed()));
        textView6.check(matches(withText("H-8 855")));

        DataInteraction linearLayout = onData(anything())
                .inAdapterView(allOf(withId(R.id.searchResults),
                        childAtPosition(
                                withClassName(is("androidx.constraintlayout.widget.ConstraintLayout")),
                                6)))
                .atPosition(0);
        linearLayout.perform(click());

        android.os.SystemClock.sleep(2000);

        ViewInteraction textView2 = onView(
                allOf(withId(R.id.toText), withText("H-8 855"),
                        isDisplayed()));
        textView2.check(matches(isDisplayed()));

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

        android.os.SystemClock.sleep(2000);

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
        appCompatEditText2.perform(replaceText("983"), closeSoftKeyboard());

        ViewInteraction textView7 = onView(
                allOf(withId(R.id.textView), withText("H-9 983"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.searchResults),
                                        0),
                                1),
                        isDisplayed()));
        textView7.check(matches(withText("H-9 983")));

        DataInteraction linearLayout2 = onData(anything())
                .inAdapterView(allOf(withId(R.id.searchResults),
                        childAtPosition(
                                withClassName(is("androidx.constraintlayout.widget.ConstraintLayout")),
                                6)))
                .atPosition(0);
        linearLayout2.perform(click());

        android.os.SystemClock.sleep(2000);

        ViewInteraction textView3 = onView(
                allOf(withId(R.id.fromText), withText("H-9 983"),
                        isDisplayed()));
        textView3.check(matches(withText("H-9 983")));

        ViewInteraction textView4 = onView(
                allOf(withId(R.id.toText), withText("H-8 855"),
                        isDisplayed()));
        textView4.check(matches(withText("H-8 855")));

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

        ViewInteraction textView5 = onView(
                allOf(withId(R.id.path_fromText), withText("H-9 983"),
                        isDisplayed()));
        textView5.check(matches(withText("H-9 983")));

        ViewInteraction textView8 = onView(
                allOf(withId(R.id.path_toText), withText("H-8 855"),
                        isDisplayed()));
        textView8.check(matches(withText("H-8 855")));

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

        android.os.SystemClock.sleep(2000);

        DataInteraction button5 = onData(anything())
                .inAdapterView(allOf(withId(R.id.FloorPickerGv)))
                .atPosition(2);
        button5.perform(click());

        android.os.SystemClock.sleep(1000);

        try{
            //take screenshot and ensure it matches expected screenshot
            Bitmap bm = Screenshot.capture().getBitmap();
            String base64 = EspressoHelpers.BitmapToBase64String(bm);
            base64.charAt(2);
            Assert.assertTrue(EspressoHelpers.imagesAreEqualExceptInfoCardHeight(bm, ScreenshotOfMap8thNoAccessibility));
            Assert.assertFalse(EspressoHelpers.imagesAreEqualExceptInfoCardHeight(bm, ScreenshotOfMap8thAccessibility));
        }
        catch(Exception e){
            e.printStackTrace();
            throw e;
        }

        DataInteraction button6 = onData(anything())
                .inAdapterView(allOf(withId(R.id.FloorPickerGv)))
                .atPosition(3);
        button6.perform(click());

        android.os.SystemClock.sleep(1000);

        try{
            //take screenshot and ensure it matches expected screenshot
            Bitmap bm = Screenshot.capture().getBitmap();
            String base64 = EspressoHelpers.BitmapToBase64String(bm);
            base64.charAt(2);
            Assert.assertTrue(EspressoHelpers.imagesAreEqualExceptInfoCardHeight(bm, ScreenshotOfMap9thNoAccessibility));
            Assert.assertFalse(EspressoHelpers.imagesAreEqualExceptInfoCardHeight(bm, ScreenshotOfMap9thAccessibility));
        }
        catch(Exception e){
            e.printStackTrace();
            throw e;
        }

        android.os.SystemClock.sleep(1000);

        ViewInteraction appCompatImageButton7 = onView(
                allOf(withId(R.id.pathsPageBackButton),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.appBarLayout),
                                        0),
                                0),
                        isDisplayed()));
        appCompatImageButton7.perform(click());

        android.os.SystemClock.sleep(1000);

        ViewInteraction appCompatImageButton8 = onView(
                allOf(withId(R.id.filterButtonDisability),
                        childAtPosition(
                                allOf(withId(R.id.linearLayout),
                                        childAtPosition(
                                                withClassName(is("androidx.constraintlayout.widget.ConstraintLayout")),
                                                5)),
                                4),
                        isDisplayed()));
        appCompatImageButton8.perform(click());

        android.os.SystemClock.sleep(1000);

        ViewInteraction appCompatImageButton4 = onView(
                allOf(withId(R.id.filterButtonWalk),
                        childAtPosition(
                                allOf(withId(R.id.linearLayout),
                                        childAtPosition(
                                                withClassName(is("androidx.constraintlayout.widget.ConstraintLayout")),
                                                5)),
                                2),
                        isDisplayed()));
        appCompatImageButton4.perform(click());

        android.os.SystemClock.sleep(1000);

        DataInteraction linearLayout7 = onData(anything())
                .inAdapterView(allOf(withId(R.id.allRoutes),
                        childAtPosition(
                                withClassName(is("androidx.coordinatorlayout.widget.CoordinatorLayout")),
                                1)))
                .atPosition(0);
        linearLayout7.perform(click());

        android.os.SystemClock.sleep(1000);

        ViewInteraction materialButton2 = onView(
                allOf(withId(R.id.SGWBtn), withText("SGW"),
                        childAtPosition(
                                allOf(withId(R.id.campusSwitchBtnContainer),
                                        childAtPosition(
                                                withId(R.id.topMapContainer),
                                                0)),
                                0),
                        isDisplayed()));
        materialButton2.perform(click());

        android.os.SystemClock.sleep(1000);

        UiObject marker2 = device.findObject(new UiSelector().descriptionContains("H"));

        try {
            marker2.click();
        } catch (UiObjectNotFoundException e) {
            e.printStackTrace();
        }

        android.os.SystemClock.sleep(2000);


        DataInteraction button7 = onData(anything())
                .inAdapterView(allOf(withId(R.id.FloorPickerGv)))
                .atPosition(2);
        button7.perform(click());

        android.os.SystemClock.sleep(1000);

        try{
            //take screenshot and ensure it matches expected screenshot
            Bitmap bm = Screenshot.capture().getBitmap();
            String base64 = EspressoHelpers.BitmapToBase64String(bm);
            base64.charAt(2);
            Assert.assertTrue(EspressoHelpers.imagesAreEqualExceptInfoCardHeight(bm, ScreenshotOfMap8thAccessibility));
            Assert.assertFalse(EspressoHelpers.imagesAreEqualExceptInfoCardHeight(bm, ScreenshotOfMap8thNoAccessibility));
        }
        catch(Exception e){
            e.printStackTrace();
            throw e;
        }

        DataInteraction button8 = onData(anything())
                .inAdapterView(allOf(withId(R.id.FloorPickerGv)))
                .atPosition(3);
        button8.perform(click());

        android.os.SystemClock.sleep(1000);

        try{
            //take screenshot and ensure it matches expected screenshot
            Bitmap bm = Screenshot.capture().getBitmap();
            String base64 = EspressoHelpers.BitmapToBase64String(bm);
            base64.charAt(2);
            Assert.assertTrue(EspressoHelpers.imagesAreEqualExceptInfoCardHeight(bm, ScreenshotOfMap9thAccessibility));
            Assert.assertFalse(EspressoHelpers.imagesAreEqualExceptInfoCardHeight(bm, ScreenshotOfMap9thNoAccessibility));
        }
        catch(Exception e){
            e.printStackTrace();
            throw e;
        }

        android.os.SystemClock.sleep(1000);

        ViewInteraction appCompatImageButton9 = onView(
                allOf(withId(R.id.pathsPageBackButton),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.appBarLayout),
                                        0),
                                0),
                        isDisplayed()));
        appCompatImageButton9.perform(click());

        android.os.SystemClock.sleep(1000);

        //disable accessibility to not affect next tests
        ViewInteraction appCompatImageButton10 = onView(
                allOf(withId(R.id.filterButtonDisability),
                        childAtPosition(
                                allOf(withId(R.id.linearLayout),
                                        childAtPosition(
                                                withClassName(is("androidx.constraintlayout.widget.ConstraintLayout")),
                                                5)),
                                4),
                        isDisplayed()));
        appCompatImageButton10.perform(click());

        android.os.SystemClock.sleep(1000);

        device.pressBack();
        android.os.SystemClock.sleep(1000);

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

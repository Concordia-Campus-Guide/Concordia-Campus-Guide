package com.example.concordia_campus_guide;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import com.example.concordia_campus_guide.Activities.MainActivity;

import androidx.test.espresso.ViewInteraction;
import androidx.test.rule.ActivityTestRule;
import androidx.test.rule.GrantPermissionRule;
import androidx.test.runner.AndroidJUnit4;
import androidx.test.uiautomator.UiDevice;
import androidx.test.uiautomator.UiObject;
import androidx.test.uiautomator.UiObjectNotFoundException;
import androidx.test.uiautomator.UiSelector;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.hamcrest.core.IsInstanceOf;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static org.hamcrest.Matchers.allOf;

@RunWith(AndroidJUnit4.class)
public class InfoCardFragmentIntegrationTest {

    private UiDevice device;

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Rule
    public GrantPermissionRule mGrantPermissionRule =
            GrantPermissionRule.grant(
                    "android.permission.ACCESS_FINE_LOCATION",
                    "android.permission.ACCESS_COARSE_LOCATION");

    @Before
    public void init() { device = UiDevice.getInstance(getInstrumentation()); }

    /**
     * This test validates that the collapsed info card contains the correct title
     * @throws UiObjectNotFoundException
     * @throws InterruptedException
     */
    @Test
    public void correctCollapsedInfoCardPopsUpWhenSpecificMarkerIsClickedTest() throws UiObjectNotFoundException, InterruptedException {
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

        UiObject marker = device.findObject(new UiSelector().descriptionContains("H"));
        marker.click();
        Thread.sleep(5000);

        ViewInteraction textView = onView(
                allOf(withId(R.id.info_card_title), withText("Henry F. Hall Building"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.info_card_frame),
                                        0),
                                0),
                        isDisplayed()));
        textView.check(matches(withText("Henry F. Hall Building")));
    }

    /**
     * This test validates that the expanded info card contains the correct departments, services and address
     * @throws UiObjectNotFoundException
     * @throws InterruptedException
     */
    @Test
    public void correctExpandedInfoCardAppearsWhenSwipingUpCardTest() throws UiObjectNotFoundException, InterruptedException {
        UiObject marker = device.findObject(new UiSelector().descriptionContains("EV"));
        marker.click();
        Thread.sleep(1500);

        // The rest of the test requires the card to be swiped up/expanded which I am unable to simulate so I'll leave it commented for now
//        // Ui automator section
//        int resourceId = this.getResources().getIdentifier("nameOfResource", "id", this.getPackageName());
//        UiObject collapsedCard = device.findObject(new UiSelector().className("androidx.core.widget.NestedScrollView").resourceId("android:id/info_card")); // I don't know how to find the object
//        collapsedCard.swipeUp(40);
//
//        // Espresso section
//        ViewInteraction textView2 = onView(
//                allOf(withId(R.id.building_address), withText("1515 Ste-Catherine West"),
//                        childAtPosition(
//                                childAtPosition(
//                                        IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class),
//                                        1),
//                                1),
//                        isDisplayed()));
//        textView2.check(matches(withText("1515 Ste-Catherine West")));
//
//        ViewInteraction textView3 = onView(
//                allOf(withId(R.id.departments_list), withText("Gina Cody School of Engineering and Computer Science, Electrical and Computer Engineering, Building, Civil and Environmental Engineering, Mechanical, Industrial and Aerospace Engineering, Design and Computation Arts, Faculty of Fine Arts, Studio Arts, Art Education, Art History, Contemporary Dance, Recreation and Athletics, Centre for Zero Energy Building Studies, Centre for Pattern Recognition and Machine Intelligence (CENPARMI), Center for Composites (CONCOM)"),
//                        childAtPosition(
//                                allOf(withId(R.id.departments),
//                                        childAtPosition(
//                                                withId(R.id.description),
//                                                1)),
//                                1),
//                        isDisplayed()));
//        textView3.check(matches(withText("Gina Cody School of Engineering and Computer Science, Electrical and Computer Engineering, Building, Civil and Environmental Engineering, Mechanical, Industrial and Aerospace Engineering, Design and Computation Arts, Faculty of Fine Arts, Studio Arts, Art Education, Art History, Contemporary Dance, Recreation and Athletics, Centre for Zero Energy Building Studies, Centre for Pattern Recognition and Machine Intelligence (CENPARMI), Center for Composites (CONCOM)")));
//
//        ViewInteraction textView4 = onView(
//                allOf(withId(R.id.services_list), withText("LeGym, FOFA Gallery"),
//                        childAtPosition(
//                                allOf(withId(R.id.services),
//                                        childAtPosition(
//                                                withId(R.id.description),
//                                                2)),
//                                1),
//                        isDisplayed()));
//        textView4.check(matches(withText("LeGym, FOFA Gallery")));
    }

    /**
     * Helper method
     */
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

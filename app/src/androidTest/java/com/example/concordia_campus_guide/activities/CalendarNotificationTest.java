//package com.example.concordia_campus_guide.activities;
//
//
//import android.view.View;
//import android.view.ViewGroup;
//import android.view.ViewParent;
//
//import androidx.test.espresso.ViewInteraction;
//import androidx.test.filters.LargeTest;
//import androidx.test.rule.ActivityTestRule;
//import androidx.test.rule.GrantPermissionRule;
//import androidx.test.runner.AndroidJUnit4;
//
//import com.example.concordia_campus_guide.R;
//import com.example.concordia_campus_guide.activities.MainActivity;
//
//import org.hamcrest.Description;
//import org.hamcrest.Matcher;
//import org.hamcrest.TypeSafeMatcher;
//import org.junit.Rule;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//
//import static androidx.test.espresso.Espresso.onView;
//import static androidx.test.espresso.action.ViewActions.click;
//import static androidx.test.espresso.action.ViewActions.scrollTo;
//import static androidx.test.espresso.assertion.ViewAssertions.matches;
//import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
//import static androidx.test.espresso.matcher.ViewMatchers.withId;
//import static androidx.test.espresso.matcher.ViewMatchers.withText;
//import static org.hamcrest.Matchers.allOf;
//
//@LargeTest
//@RunWith(AndroidJUnit4.class)
//public class CalendarNotificationTest {
//
//    @Rule
//    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);
//
//    @Rule
//    public GrantPermissionRule mGrantPermissionRule =
//            GrantPermissionRule.grant(
//                    "android.permission.ACCESS_FINE_LOCATION",
//                    "android.permission.ACCESS_COARSE_LOCATION",
//                    "android.permission.WRITE_CALENDAR",
//                    "android.permission.READ_CALENDAR");
//
//    @Test
//    public void calendarNotificationTest() {
//
//        android.os.SystemClock.sleep(5000);
//
//        ViewInteraction linearLayout = onView(
//                allOf(withId(R.id.buttonPanel),
//                        isDisplayed()));
//        linearLayout.check(matches(isDisplayed()));
//
//        ViewInteraction textView = onView(
//                allOf(withId(android.R.id.message),
//                        isDisplayed()));
//        textView.check(matches(isDisplayed()));
//
//        ViewInteraction textView2 = onView(
//                allOf(withId(R.id.alertTitle),
//                        isDisplayed()));
//        textView2.check(matches(isDisplayed()));
//
//        ViewInteraction button = onView(
//                allOf(withId(android.R.id.button1),
//                        isDisplayed()));
//        button.check(matches(isDisplayed()));
//
//        ViewInteraction button2 = onView(
//                allOf(withId(android.R.id.button2),
//                        isDisplayed()));
//        button2.check(matches(isDisplayed()));
//
//        ViewInteraction materialButton = onView(
//                allOf(withId(android.R.id.button1), withText("Show me Directions"),
//                        childAtPosition(
//                                childAtPosition(
//                                        withId(R.id.buttonPanel),
//                                        0),
//                                3)));
//        materialButton.perform(scrollTo(), click());
//
//        android.os.SystemClock.sleep(2000);
//
//        ViewInteraction textView3 = onView(
//                allOf(withId(R.id.toText), withText("H-9 937"),
//                        isDisplayed()));
//        textView3.check(matches(withText("H-9 937")));
//    }
//
//    private static Matcher<View> childAtPosition(
//            final Matcher<View> parentMatcher, final int position) {
//
//        return new TypeSafeMatcher<View>() {
//            @Override
//            public void describeTo(Description description) {
//                description.appendText("Child at position " + position + " in parent ");
//                parentMatcher.describeTo(description);
//            }
//
//            @Override
//            public boolean matchesSafely(View view) {
//                ViewParent parent = view.getParent();
//                return parent instanceof ViewGroup && parentMatcher.matches(parent)
//                        && view.equals(((ViewGroup) parent).getChildAt(position));
//            }
//        };
//    }
//}

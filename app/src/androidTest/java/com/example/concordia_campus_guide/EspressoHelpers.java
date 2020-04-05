package com.example.concordia_campus_guide;

import android.view.View;

import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.matcher.ViewMatchers;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

public class EspressoHelpers {

    //espresso is unable to click objects that are partially hidden, so this method circumvents that
    public static ViewAction hiddenClick(){
        return new ViewAction() {
            @Override
            public Matcher<View> getConstraints() {
                return ViewMatchers.isEnabled(); // no constraints, they are checked above
            }

            @Override
            public String getDescription() {
                return "click the button";
            }

            @Override
            public void perform(UiController uiController, View view) {
                view.performClick();
            }
        };
    }

    //espresso has issues matching elements when there are multiple elements with the same id
    public static Matcher<View> getElementFromMatchAtPosition(final Matcher<View> matcher, final int position) {
        return new BaseMatcher<View>() {
            int counter = 0;
            @Override
            public boolean matches(final Object item) {
                if (matcher.matches(item)) {
                    if(counter == position) {
                        counter++;
                        return true;
                    }
                    counter++;
                }
                return false;
            }

            @Override
            public void describeTo(final Description description) {
                description.appendText("Element at hierarchy position "+position);
            }
        };
    }

    //cancel ignore card if there is an event notification
    //this allows to test the rest of the functionality in the event the user has/has not an event in their calendar
    public static void cancelNotificationIfExists(){
        android.os.SystemClock.sleep(2500);

        ViewInteraction materialButtonX = onView(
                allOf(withId(android.R.id.button2), withText("Ignore"),
                        isDisplayed()));
        try{
            materialButtonX.perform(scrollTo(), click());
        }
        catch(Exception e){
            //ignore maybe the popup was cancelled by other test
        }
    }
}
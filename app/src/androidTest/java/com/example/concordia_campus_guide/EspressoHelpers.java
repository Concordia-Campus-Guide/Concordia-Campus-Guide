package com.example.concordia_campus_guide;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.util.Base64;
import android.view.View;

import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.platform.app.InstrumentationRegistry;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

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

    //used for screenshot testing
    public static String BitmapToBase64String(Bitmap bitmap){
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream .toByteArray();

        String encoded = Base64.encodeToString(byteArray, Base64.DEFAULT);
        return encoded;
    }

    public static Bitmap importScreenshot(String path){
        String fileString = null;

        try
        {
            AssetManager am = InstrumentationRegistry.getInstrumentation().getContext().getAssets();
            InputStream inputStream = am.open(path);
            Scanner s = new Scanner(inputStream).useDelimiter("\\A");
            fileString =s.hasNext() ? s.next() : "";
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        byte[] decodedString = Base64.decode(fileString, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
    }

    public static boolean imagesAreEqualExceptInfoCardHeight(Bitmap bm1, Bitmap bm2){
        if (bm1.getHeight() != bm2.getHeight() || bm1.getWidth() != bm2.getWidth()){
            return false;
        }

        Bitmap bmp = bm1.copy(bm1.getConfig(), true);
        int threshold = 10;
        boolean isSame = true;

        for (int i = 0; i < bm1.getWidth(); i++)
        {
            //skip notifications, time and info card
            for (int j = 50; j < bm1.getHeight()-500; j++)
            {
                int pixel = bm1.getPixel(i,j);
                int redValue = Color.red(pixel);
                int blueValue = Color.blue(pixel);
                int greenValue = Color.green(pixel);

                int pixel2 = bm2.getPixel(i,j);
                int redValue2 = Color.red(pixel2);
                int blueValue2 = Color.blue(pixel2);
                int greenValue2 = Color.green(pixel2);

                if (Math.abs(redValue2 - redValue) + Math.abs(blueValue2 - blueValue) + Math.abs(greenValue2 - greenValue) <= threshold) {
                    //all good
                }
                else{
                    bmp.setPixel(i,j, Color.YELLOW); //for debugging purposes, uncomment to see areas that are different in variable bmp
                    isSame = false;
                }
            }
        }

        return isSame;
    }
}
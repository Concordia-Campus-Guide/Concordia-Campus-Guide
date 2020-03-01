package com.example.concordia_campus_guide;

import androidx.test.runner.AndroidJUnit4;
import androidx.test.uiautomator.UiDevice;
import androidx.test.uiautomator.UiObject;
import androidx.test.uiautomator.UiObjectNotFoundException;
import androidx.test.uiautomator.UiSelector;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;

@RunWith(AndroidJUnit4.class)
public class InfoCardFragmentIntegrationTest {

    @Before
    public void init() {

    }

    @Test
    public void testMarker() throws UiObjectNotFoundException {
        UiDevice device = UiDevice.getInstance(getInstrumentation());
        UiObject marker = device.findObject(new UiSelector().descriptionContains("EV"));
        marker.click();
//        UiObject button = device.findObject(new UiSelector().descriptionContains("Directions"));
//        button.click();
    }
}

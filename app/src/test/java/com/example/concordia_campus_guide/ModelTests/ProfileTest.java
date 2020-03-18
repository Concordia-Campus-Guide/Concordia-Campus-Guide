package com.example.concordia_campus_guide.ModelTests;

import com.example.concordia_campus_guide.Models.Profile;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;

public class ProfileTest {
    private Profile profile;

    @Before
    public void init() {
        profile = new Profile();
    }

    @Test
    public void toggleHandicapTest(){
        assertFalse(profile.isHandicapAccess());
        assertTrue(profile.toggleHandicap());
        assertFalse(profile.toggleHandicap());
    }

    @Test
    public void toggleStaffTest(){
        assertFalse(profile.isStaffAccess());
        assertTrue(profile.toggleStaff());
        assertFalse(profile.toggleStaff());
    }

    @Test
    public void getAndSetlanguageTest(){
        assertEquals(profile.getLanguage(), null);
    }

}

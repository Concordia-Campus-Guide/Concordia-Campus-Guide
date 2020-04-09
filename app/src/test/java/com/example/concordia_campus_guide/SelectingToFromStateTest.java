package com.example.concordia_campus_guide;

import android.location.Location;

import com.example.concordia_campus_guide.Global.SelectingToFromState;
import com.example.concordia_campus_guide.Models.Coordinates;
import com.example.concordia_campus_guide.Models.Place;
import com.example.concordia_campus_guide.Models.RoomModel;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class SelectingToFromStateTest {
        private RoomModel fromRoom;
        private RoomModel toRoom;

        @Before
        public void init(){
            fromRoom = new RoomModel(new Coordinates(-73.57907921075821, 45.49702057370776), "823", "H-8", "SGW");
            toRoom  = new RoomModel(new Coordinates(-73.57902321964502, 45.49699848270905), "921", "H-9", "SGW");
            SelectingToFromState.setFrom(fromRoom);
            SelectingToFromState.setTo(toRoom);
        }


        @Test
        public void getFromTest(){
            assertEquals(fromRoom,SelectingToFromState.getFrom());
        }

        @Test
        public void getToTest(){
            assertEquals(toRoom,SelectingToFromState.getTo());
        }

        @Test
        public void setQuickSelectToTrueTest(){
            SelectingToFromState.setQuickSelectToTrue();
            assertTrue(SelectingToFromState.isQuickSelect());
            assertFalse(SelectingToFromState.isSelectFrom());
            assertFalse(SelectingToFromState.isSelectTo());
        }

        @Test
        public void setSelectToToTrueTest(){
            SelectingToFromState.setSelectToToTrue();
            assertFalse(SelectingToFromState.isQuickSelect());
            assertFalse(SelectingToFromState.isSelectFrom());
            assertTrue(SelectingToFromState.isSelectTo());
        }

        @Test
        public void setSelectFromToTrueTest(){
            SelectingToFromState.setSelectFromToTrue();
            assertFalse(SelectingToFromState.isQuickSelect());
            assertTrue(SelectingToFromState.isSelectFrom());
            assertFalse(SelectingToFromState.isSelectTo());
        }

        @Test
        public void resetTest(){
            SelectingToFromState.reset();
            assertTrue(SelectingToFromState.isQuickSelect());
        }

}

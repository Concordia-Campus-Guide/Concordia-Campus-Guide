package com.example.concordia_campus_guide.Models;

import java.util.ArrayList;
import java.util.List;

public class WalkingPoints {
        private List<WalkingPoint> walkingPoints;

        public WalkingPoints(){ walkingPoints = new ArrayList<>(); }

        public WalkingPoints(List<WalkingPoint> walkingPoints){  this.walkingPoints = walkingPoints; }

        public List<WalkingPoint> getWalkingPoints(){
            return walkingPoints;
        }

        public void setWalkingPoints(List<WalkingPoint> walkingPoints){ this.walkingPoints = walkingPoints; }

}


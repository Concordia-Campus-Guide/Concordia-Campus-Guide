package com.example.concordia_campus_guide.Models;

import java.util.ArrayList;
import java.util.List;

public class WalkingPoints {
        private List<WalkingPoint> walkingPointList;

        public WalkingPoints(){ walkingPointList = new ArrayList<>(); }

        public WalkingPoints(List<WalkingPoint> walkingPoints){  this.walkingPointList = walkingPoints; }

        public List<WalkingPoint> getWalkingPointList(){
            return walkingPointList;
        }

        public void setWalkingPointList(List<WalkingPoint> walkingPointList){ this.walkingPointList = walkingPointList; }

}


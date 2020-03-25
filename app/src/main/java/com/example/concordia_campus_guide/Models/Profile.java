package com.example.concordia_campus_guide.Models;

import java.util.List;
import java.util.Queue;


public class Profile {
   // @ColumnInfo (name ="handicapAccess")
    private boolean handicapAccess;

   // @ColumnInfo (name ="staffAccess")
    private boolean staffAccess;

    //@ColumnInfo (name = "language")
    private Language language;

    List<Place> savedPlaces;

    Queue<Place> history;

    public boolean toggleHandicap(){

        handicapAccess = handicapAccess? false: true;
        return handicapAccess;
    }

    public boolean toggleStaff(){
        staffAccess = staffAccess? false: true;
        return staffAccess;
    }

    public boolean isHandicapAccess() {
        return handicapAccess;
    }

//    public void setHandicapAccess(boolean handicapAccess) {
//        this.handicapAccess = handicapAccess;
//    }

    public boolean isStaffAccess() {
        return staffAccess;
    }

//    public void setStaffAccess(boolean staffAccess) {
//        this.staffAccess = staffAccess;
//    }

    public Language getLanguage() {
        return language;
    }
    public void setLanguage(Language language){
        this.language = language;
    }
}

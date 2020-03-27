package com.example.concordia_campus_guide.Models;

public class Profile {
    private boolean handicapAccess;
    private boolean staffAccess;
    private Language language;

    public boolean toggleHandicap() {
        handicapAccess = !handicapAccess;
        return handicapAccess;
    }

    public boolean toggleStaff() {
        staffAccess = !staffAccess;
        return staffAccess;
    }

    public boolean isHandicapAccess() {
        return handicapAccess;
    }

    public boolean isStaffAccess() {
        return staffAccess;
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }
}

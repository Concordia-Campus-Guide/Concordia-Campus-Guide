package com.example.concordia_campus_guide.Global;

import com.example.concordia_campus_guide.Models.Profile;

import java.util.List;


public class User {

    public static User instance;

    private long id;
    private List schedule;
    private Profile profile;

    private User() {}

    public User getInstance() {
        return instance;
    }
}

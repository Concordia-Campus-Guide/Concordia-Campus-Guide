package com.example.concordia_campus_guide.Global;

import com.example.concordia_campus_guide.Models.Profile;

import java.util.List;

import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.Ignore;

@Entity (tableName = "users")
public class User {

    @Ignore
    public static User instance;

    @ColumnInfo (name ="id")
    private long id;


    private List schedule;

    @Embedded
    private Profile profile;

    public User() {}

    private void fetchSchedule() {}

    public User getInstance() {
        return instance;
    }
}

package com.example.concordia_campus_guide.Models;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.concordia_campus_guide.Database.Converters.StringListConverter;

import java.util.List;

@Entity(tableName = "shuttle")
public class Shuttle {
    @PrimaryKey(autoGenerate = true)
    private int shuttleId;

    @ColumnInfo(name = "campus")
    private String campus;

    @ColumnInfo(name = "day")
    @TypeConverters(StringListConverter.class)
    private List<String> day;

    @ColumnInfo(name = "time")
    private String time;

    @Ignore
    private List<Shuttle> shuttle;

    public Shuttle() {
    }

    public List<Shuttle> getShuttle() {
        return shuttle;
    }

    public int getShuttleId() {
        return shuttleId;
    }

    public void setShuttleId(int shuttleId) {
        this.shuttleId = shuttleId;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getCampus() {
        return campus;
    }

    public void setCampus(String campus) {
        this.campus = campus;
    }

    public List<String> getDay() {
        return day;
    }

    public void setDay(List<String> day) {
        this.day = day;
    }
}

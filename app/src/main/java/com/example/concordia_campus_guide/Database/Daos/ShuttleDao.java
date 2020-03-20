package com.example.concordia_campus_guide.Database.Daos;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.concordia_campus_guide.Models.RoomModel;
import com.example.concordia_campus_guide.Models.Shuttle;

import java.util.List;

@Dao
public interface ShuttleDao {
    @Insert
    void insertAll(List<Shuttle> shuttle);

    @Query("SELECT * FROM shuttle")
    List<Shuttle> getAll();

    @Query("SELECT *  FROM shuttle WHERE campus=:campus")
    List<Shuttle> getScheduleByCampus(String campus);

    @Query("SELECT *  FROM shuttle WHERE campus=:campus AND day LIKE '%' || :day || '%' ")
    List<Shuttle> getScheduleByCampusAndDay(String campus, String day);

    @Query("SELECT *  FROM shuttle WHERE campus=:campus AND day LIKE '%' || :day || '%' AND time >= :time")
    List<Shuttle> getScheduleByCampusAndDayAndTime(String campus, String day, Double time);
}

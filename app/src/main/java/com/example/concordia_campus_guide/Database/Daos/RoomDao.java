package com.example.concordia_campus_guide.Database.Daos;


import com.example.concordia_campus_guide.Models.Building;
import com.example.concordia_campus_guide.Models.Floor;
import com.example.concordia_campus_guide.Models.RoomModel;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Room;

@Dao
public interface RoomDao {

//    Fix the query here later
    @Query("SELECT *  FROM rooms")
    List<RoomModel> getAllRoomsForFloorInBuiling();


    @Insert
    void insertAllRooms(RoomModel... rooms);


    @Delete
    void deleteAll(RoomModel... rooms);


}

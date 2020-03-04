package com.example.concordia_campus_guide.Database.Daos;


import com.example.concordia_campus_guide.Models.Floor;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Room;

@Dao
public interface RoomDao {

    @Query("SELECT room_number FROM rooms, floors, buildings WHERE  " +
            "rooms.floor_number = floors.number and floors.buildingCode = buildings.buildingCode")
    List<Floor> getAllRoomsForFloorInBuiling();


    @Insert
    void insertAllRooms(Room... rooms);


}

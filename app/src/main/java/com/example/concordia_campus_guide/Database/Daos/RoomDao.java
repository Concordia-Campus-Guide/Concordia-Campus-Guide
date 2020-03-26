package com.example.concordia_campus_guide.Database.Daos;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.concordia_campus_guide.Models.RoomModel;

import java.util.List;

@Dao
public interface RoomDao {

    @Query("SELECT *  FROM rooms WHERE floor_code=:floorCode")
    List<RoomModel> getAllRoomsByFloorCode(String floorCode);

    @Query("SELECT * FROM rooms")
    List<RoomModel> getAll();

    @Insert
    void insertAll(List<RoomModel> rooms);

    @Delete
    void deleteAll(RoomModel... rooms);

    @Query("SELECT * FROM rooms WHERE id=:roomId")
    public RoomModel getRoomById(long roomId);

    @Query("SELECT * FROM rooms WHERE room_code=:roomCode AND floor_code=:floorCode" )
    public RoomModel getRoomByRoomCodeAndFloorCode(String roomCode, String floorCode);


    @Query("SELECT * FROM rooms WHERE id=:roomId AND floor_code=:floorCode" )
    public RoomModel getRoomByIdAndFloorCode(long roomId, String floorCode);
}

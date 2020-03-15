package com.example.concordia_campus_guide.Database.Daos;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

import com.example.concordia_campus_guide.Models.Floor;
import com.example.concordia_campus_guide.Models.Relations.FloorWithRooms;

import java.util.List;

@Dao
public interface FloorDao {
    
    @Query("SELECT * FROM floors")
    List<Floor> getAll();

    @Insert
    void insertAll(List<Floor> floors);

    @Delete
    void deleteAll(Floor... floors);

    @Transaction
    @Query("SELECT * FROM floors")
    public List<FloorWithRooms> getFloorWithRooms();

    @Query("SELECT * FROM floors WHERE id=:floorId")
    public Floor getFloorById(long floorId);
}

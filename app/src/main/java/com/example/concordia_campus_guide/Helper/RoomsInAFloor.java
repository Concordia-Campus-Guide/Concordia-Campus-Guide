package com.example.concordia_campus_guide.Helper;

import com.example.concordia_campus_guide.Database.AppDatabase;
import com.example.concordia_campus_guide.Models.RoomModel;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class RoomsInAFloor {

    private MutableLiveData<List<RoomModel>> roomList = new MutableLiveData<>();

    public void setListOfRooms(String floorCode, AppDatabase appDatabase) {
        List<RoomModel> allRoomsOnFloor = appDatabase.roomDao().getAllRoomsByFloorCode(floorCode);
        this.roomList.postValue(allRoomsOnFloor);
    }

    public LiveData<List<RoomModel>> getListOfRoom() {
        return roomList;
    }
}

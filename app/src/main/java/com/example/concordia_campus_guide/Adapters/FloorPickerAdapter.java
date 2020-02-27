package com.example.concordia_campus_guide.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;

import com.example.concordia_campus_guide.BuildingCode;
import com.example.concordia_campus_guide.Interfaces.OnFloorPickerOnClickListener;

import java.util.ArrayList;

public class FloorPickerAdapter extends BaseAdapter {
    private OnFloorPickerOnClickListener listener;
    private Context context;
    private BuildingCode buildingCode;
    private ArrayList<String> floorsAvailable;

    public FloorPickerAdapter(Context context, ArrayList floorsAvailable, BuildingCode buildingCode, OnFloorPickerOnClickListener listener){
        this.context = context;
        this.floorsAvailable = floorsAvailable;
        this.buildingCode = buildingCode;
        this.listener = listener;
        System.out.println("DEBUGGER: floorsavailable" + floorsAvailable.get(0) + "\tsize: " + floorsAvailable.size());
    }

    @Override
    public int getCount() {
        return floorsAvailable.size();
    }

    @Override
    public Object getItem(int i) {
        return floorsAvailable.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, final View view, ViewGroup viewGroup) {
        Button button;
        if (view == null) {
            button = new Button(context);
            button.setTextColor(Color.parseColor("#FFFFFF"));
            button.setLayoutParams(new GridView.LayoutParams(120, 120));
            String floor = floorsAvailable.get(i);
            floor = floor.substring(floor.indexOf('_') + 1);
            System.out.print("testing substring + " + floor);
            button.setText(floor);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onFloorPickerOnClick(i, view);
                }
            });
        } else {
            button = (Button) view;
        }
        return button;
    }

    public BuildingCode getBuildingCode(){
        return buildingCode;
    }
}
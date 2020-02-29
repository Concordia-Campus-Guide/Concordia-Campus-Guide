package com.example.concordia_campus_guide.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;

import com.example.concordia_campus_guide.BuildingCode;
import com.example.concordia_campus_guide.Interfaces.OnFloorPickerOnClickListener;
import com.example.concordia_campus_guide.R;

import java.util.ArrayList;

import androidx.core.content.ContextCompat;

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
    }

    @Override
    public int getCount() {
        return floorsAvailable.size();
    }

    @Override
    public Object getItem(int position) {
        return floorsAvailable.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, final View view, ViewGroup viewGroup) {
        Button button;
        if (view == null) {
            button = new Button(context);
            button.setTextColor(context.getResources().getColor(R.color.floorPickerTextColor));
            button.setLayoutParams(new GridView.LayoutParams(120, 120));
            String floor = floorsAvailable.get(position);
            floor = floor.substring(floor.indexOf('_') + 1);
            Log.i("FloorPickerAdapter","testing substring + " + floor);
            button.setText(floor);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onFloorPickerOnClick(position, v);
                }
            });
            button.setBackground(ContextCompat.getDrawable(context, R.drawable.button_highlights));
        } else {
            button = (Button) view;
        }
        return button;
    }

    public BuildingCode getBuildingCode(){
        return buildingCode;
    }
}
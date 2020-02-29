package com.example.concordia_campus_guide.Adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;

import com.example.concordia_campus_guide.Interfaces.OnFloorPickerOnClickListener;
import com.example.concordia_campus_guide.R;

import androidx.core.content.ContextCompat;

public class FloorPickerAdapter extends BaseAdapter {
    private OnFloorPickerOnClickListener listener;
    private Context context;
    private String buildingCode;
    private String[] floorsAvailable;

    public FloorPickerAdapter(Context context, String[] floorsAvailable, String buildingCode, OnFloorPickerOnClickListener listener){
        this.context = context;
        this.floorsAvailable = floorsAvailable;
        this.buildingCode = buildingCode;
        this.listener = listener;
    }

    @Override
    public int getCount() {
        return floorsAvailable.length;
    }

    @Override
    public Object getItem(int position) {
        return floorsAvailable[position];
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
            button.setText(floorsAvailable[position]);
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

    public String getBuildingCode(){
        return buildingCode;
    }
    public String[] getFloorsAvailable() {
        return floorsAvailable;
    }

    public void setFloorsAvailable(String[] floorsAvailable) {
        this.floorsAvailable = floorsAvailable;
    }
}
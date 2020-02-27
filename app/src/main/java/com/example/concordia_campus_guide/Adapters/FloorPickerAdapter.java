package com.example.concordia_campus_guide.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import java.util.ArrayList;

public class FloorPickerAdapter extends BaseAdapter {
    private Context context;
    String buildingCode;
    ArrayList<Integer> floorsAvailable;
    public FloorPickerAdapter(Context context, ArrayList floorsAvailable){
        this.context = context;
        this.floorsAvailable = floorsAvailable;
    }

    @Override
    public int getCount() {
        return floorsAvailable.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Button button;
        if (view == null) {
            button = new Button(context);
            button.setTextColor(Color.parseColor("#FFFFFF"));
            button.setLayoutParams(new GridView.LayoutParams(120, 120));
            button.setText(String.valueOf(String.valueOf(floorsAvailable.get(i))));
        } else {
            button = (Button) view;
        }
        return button;
    }
}
package com.example.concordia_campus_guide.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;

public class FloorPickerAdapter extends BaseAdapter {
    private Context context;
    private int size;
    public FloorPickerAdapter(Context context, int size){
        this.context = context;
        this.size = size;
    }

    @Override
    public int getCount() {
        return size;
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
        if(view == null){
            button = new Button(context);
            button.setTextColor(Color.parseColor("#FFFFFF"));
            button.setLayoutParams(new GridView.LayoutParams(120, 120));
            button.setText("3");
            button.setPadding(8, 8, 8, 8);
        }
        else {
            button = (Button) view;
        }
        return button;
    }
}
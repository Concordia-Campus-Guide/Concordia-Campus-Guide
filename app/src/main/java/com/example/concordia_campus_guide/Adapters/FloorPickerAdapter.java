package com.example.concordia_campus_guide.Adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;

import com.example.concordia_campus_guide.Interfaces.OnFloorPickerOnClickListener;
import com.example.concordia_campus_guide.R;

import java.util.List;

import androidx.core.content.ContextCompat;

public class FloorPickerAdapter extends BaseAdapter {
    private OnFloorPickerOnClickListener listener;
    private Context context;
    private String buildingCode;
    private List<String> floorsAvailable;
    private Button selectedButton;

    public FloorPickerAdapter(Context context, List<String> floorsAvailable, String buildingCode, OnFloorPickerOnClickListener listener){
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
    public Object getItem(int position) { return floorsAvailable.get(position); }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, final View convertView, ViewGroup viewGroup) {
        Button button;
        if (convertView == null) {
            button = new Button(context);
            button.setTextColor(context.getResources().getColor(R.color.floorPickerTextColor));
            button.setLayoutParams(new GridView.LayoutParams(120, 120));
            button.setText(floorsAvailable.get(position));
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(selectedButton != null && !selectedButton.equals((Button)view)){
                        selectedButton.setEnabled(true);
                    }
                    selectedButton = (Button)view;
                    selectedButton.setEnabled(false);
                    listener.onFloorPickerOnClick(position, view);
                }
            });
            button.setBackground(ContextCompat.getDrawable(context, R.drawable.button_highlights));
        } else {
            button = (Button) convertView;
        }

        if(position == floorsAvailable.size() - 1){
            selectedButton = button;
            selectedButton.setEnabled(false);
        }
        return button;
    }

    public String getBuildingCode(){
        return buildingCode;
    }
    public List<String> getFloorsAvailable() {
        return floorsAvailable;
    }

    public void setFloorsAvailable(List<String> floorsAvailable) {
        this.floorsAvailable = floorsAvailable;
    }
}
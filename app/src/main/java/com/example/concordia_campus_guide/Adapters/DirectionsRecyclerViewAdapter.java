package com.example.concordia_campus_guide.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.concordia_campus_guide.Models.Direction;
import com.example.concordia_campus_guide.R;

import java.text.DecimalFormat;
import java.util.List;

public class DirectionsRecyclerViewAdapter extends RecyclerView.Adapter<DirectionsRecyclerViewAdapter.ViewHolder> {

    private static DecimalFormat df2 = new DecimalFormat("#.##");
    private List<Direction> mData;
    private LayoutInflater mInflater;

    // data is passed into the constructor
    public DirectionsRecyclerViewAdapter(Context context, List<Direction> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.direction_list_item_layout, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String description = mData.get(position).getDescription();
        String duration = String.valueOf(df2.format(mData.get(position).getDuration()))+"min";
        holder.directionInstruction.setText(description);
        holder.duration.setText(duration);
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mData.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView directionInstruction;
        TextView duration;

        ViewHolder(View itemView) {
            super(itemView);
            directionInstruction = itemView.findViewById(R.id.direction_instruction);
            duration = itemView.findViewById(R.id.direction_duration);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            //do nothing
        }
    }
}


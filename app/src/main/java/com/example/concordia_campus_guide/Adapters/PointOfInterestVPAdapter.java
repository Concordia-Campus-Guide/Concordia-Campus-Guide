package com.example.concordia_campus_guide.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.GridView;
import android.widget.ListAdapter;

import com.example.concordia_campus_guide.Interfaces.OnPOIClickListener;
import com.example.concordia_campus_guide.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class PointOfInterestVPAdapter extends RecyclerView.Adapter<PointOfInterestVPAdapter.MyViewHolder> {

    private List<String> services;
    private Context context;
    private OnPOIClickListener onClickListener;

    public PointOfInterestVPAdapter(Context context, List<String> services, OnPOIClickListener onClickListener) {
        this.services = services;
        this.context = context;
        this.onClickListener = onClickListener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.point_of_interest_list_layout, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Adapter poiGvAdapter = new PointOfInterestGvAdapter(context, services.subList(position*4, Math.min((position*4)+4, services.size())), onClickListener);
        holder.poiGV.setAdapter((ListAdapter) poiGvAdapter);

    }

    @Override
    public int getItemCount() {
        return Math.min((services.size()/4)+1, (services.size()%4)+(services.size()/4));
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        GridView poiGV;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            poiGV = itemView.findViewById(R.id.poiGv);
        }
    }
}
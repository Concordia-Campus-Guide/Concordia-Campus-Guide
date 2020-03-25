package com.example.concordia_campus_guide.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.example.concordia_campus_guide.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class PoiAdapter extends RecyclerView.Adapter<PoiAdapter.MyViewHolder> {

    private List<String> services;
    private LayoutInflater layoutInflater;
    private Context context;

    public PoiAdapter(Context context, List<String> services) {
        this.services = services;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_poi_layout, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Adapter adapter = new PoiGvAdapter(context, services.subList(position*4, Math.min((position*4)+4, services.size())));
        holder.poiGV.setAdapter((ListAdapter) adapter);

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
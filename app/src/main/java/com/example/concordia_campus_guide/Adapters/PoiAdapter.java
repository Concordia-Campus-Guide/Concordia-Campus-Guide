package com.example.concordia_campus_guide.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

        holder.service1.setText(services.get((position*4)+0));
        holder.service2.setText(services.get((position*4)+1));
        holder.service3.setText(services.get((position*4)+2));
        holder.service4.setText(services.get((position*4)+3));

    }

    @Override
    public int getItemCount() {
        return Math.min((services.size()/4)+1, (services.size()%4)+(services.size()/4));
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView service1;
        TextView service2;
        TextView service3;
        TextView service4;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            service1 = itemView.findViewById(R.id.service1Tv);
            service2 = itemView.findViewById(R.id.service2Tv);
            service3 = itemView.findViewById(R.id.service3Tv);
            service4 = itemView.findViewById(R.id.service4Tv);
        }
    }
}
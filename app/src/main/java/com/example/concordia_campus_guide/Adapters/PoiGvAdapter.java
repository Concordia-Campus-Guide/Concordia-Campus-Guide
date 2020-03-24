package com.example.concordia_campus_guide.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.concordia_campus_guide.R;

import java.util.List;

class PoiGvAdapter extends BaseAdapter {
    private Context context;
    List<String> services;
    LayoutInflater layoutInflater;

    public PoiGvAdapter(Context context, List<String> services){
        this.context = context;
        this.services = services;
        layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return services.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.point_of_interest_item, parent, false);
        TextView serviceTxt = view.findViewById(R.id.serviceTv);
        serviceTxt.setText(services.get(position));
        return view;
    }
}

package com.example.concordia_campus_guide.Adapters;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.concordia_campus_guide.Interfaces.OnPOIClickListener;
import com.example.concordia_campus_guide.R;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

class PointOfInterestGvAdapter extends BaseAdapter {
    private Context context;
    private List<String> services;
    private OnPOIClickListener onClickListener;

    public PointOfInterestGvAdapter(Context context, List<String> services, OnPOIClickListener onClickListener){
        this.context = context;
        this.services = services;
        this.onClickListener = onClickListener;
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.point_of_interest_item, parent, false);

        TextView serviceTxt = view.findViewById(R.id.serviceTv);
        ImageView serviceIv = view.findViewById(R.id.serviceIv);

        serviceIv.setImageBitmap(getBitmapFromAssets("point_of_interest_icons/poi_"+services.get(position).toLowerCase()+".png"));

        int serviceTextId = context.getResources().getIdentifier(services.get(position).toLowerCase(), "string", context.getPackageName());
        serviceTxt.setText(context.getResources().getString(serviceTextId));

        view.setOnClickListener(view1 -> onClickListener.onClick(services.get(position), view1));

        return view;
    }

    private Bitmap getBitmapFromAssets(String fileName){
        AssetManager assetManager = context.getAssets();
        InputStream inputStream = null;
        try{
            inputStream = assetManager.open(fileName);
        } catch(IOException e){
            Log.e("BITMAP FROM ASSETS ERROR", e.getMessage());
        }

        return BitmapFactory.decodeStream(inputStream);
    }
}

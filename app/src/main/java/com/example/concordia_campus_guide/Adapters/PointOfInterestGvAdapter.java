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

import com.example.concordia_campus_guide.R;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

class PointOfInterestGvAdapter extends BaseAdapter {
    private Context context;
    List<String> services;

    public PointOfInterestGvAdapter(Context context, List<String> services){
        this.context = context;
        this.services = services;
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
        ImageView serviceIv = view.findViewById(R.id.serviceIv);

        serviceIv.setImageBitmap(getBitmapFromAssets("point_of_interest_icons/poi_"+services.get(position)+".png"));
        serviceTxt.setText(services.get(position).replace("_", " "));

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

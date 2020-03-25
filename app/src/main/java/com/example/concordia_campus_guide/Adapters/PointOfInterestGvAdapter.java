package com.example.concordia_campus_guide.Adapters;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.concordia_campus_guide.R;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;

import java.io.IOException;
import java.io.InputStream;
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
        ImageView serviceIv = view.findViewById(R.id.serviceIv);
        serviceIv.setImageBitmap(getBitmapFromAssets("point_of_interest_icons/poi_"+services.get(position)+".png"));
        serviceTxt.setText(services.get(position));
        return view;
    }
    private Bitmap getBitmapFromAssets(String fileName){
        AssetManager am = context.getAssets();
        InputStream is = null;
        try{
            is = am.open(fileName);
        }catch(IOException e){
            e.printStackTrace();
        }
        Bitmap bitmap = BitmapFactory.decodeStream(is);
        return bitmap;
    }
}

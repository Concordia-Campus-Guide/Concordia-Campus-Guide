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
import com.example.concordia_campus_guide.Models.PoiType;
import com.example.concordia_campus_guide.R;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Locale;

import static android.content.Context.MODE_PRIVATE;

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
        serviceTxt.setText(setPOIServiceText(services.get(position).toLowerCase()));


        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickListener.onClick(services.get(position), view);
            }
        });

        return view;
    }

    private String setPOIServiceText(String serviceText) {
        // TODO: extract the hard-coded strings and add to res
        if( Locale.getDefault().getLanguage() == "fr"){
            if(serviceText.contains(PoiType.ELEVATOR.toLowerCase())){
                return "ASCENCEURS";
            }
            if(serviceText.contains(PoiType.LOUNGES.toLowerCase())){
                return "SALONS";
            }
            if(serviceText.contains(PoiType.STAIRS.toLowerCase())){
                return "ESCALIERS";
            }
            if(serviceText.contains(PoiType.WASHROOM.toLowerCase())){
                return "TOILETTES";
            }
            if(serviceText.contains(PoiType.WATER_FOUNTAINS.toLowerCase())){
                return "FONTAINES D\'EAU";
            }
        }
        return serviceText;
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

package com.example.concordia_campus_guide.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.concordia_campus_guide.Models.Building;
import com.example.concordia_campus_guide.Models.Floor;
import com.example.concordia_campus_guide.Models.Place;
import com.example.concordia_campus_guide.Models.RoomModel;
import com.example.concordia_campus_guide.R;

import java.util.ArrayList;
import java.util.List;

public class PlaceToSearchResultAdapter extends ArrayAdapter<Place> implements Filterable {

    Context c;
    List<Place> places;
    List<Place> originalPlaces;
    CustomFilter cs;

    private static class ViewHolder {
        private TextView itemView;
        private ImageView imageView;
    }

    public PlaceToSearchResultAdapter(Context context, int textViewResourceId, List<Place> items) {
        super(context, textViewResourceId, items);
        c = context;
        places = items;
        originalPlaces = items;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;

        if (convertView == null) {
            convertView = LayoutInflater.from(this.getContext())
                    .inflate(R.layout.list_item_layout, parent, false);

            viewHolder = new ViewHolder();
            viewHolder.itemView = convertView.findViewById(R.id.textView);
            viewHolder.imageView = convertView.findViewById(R.id.place_icon);

            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Place item = getItem(position);
        if (item!= null) {
            viewHolder.itemView.setText(item.getDisplayName());
            viewHolder.imageView.setImageResource(getIcon(item));
        }

        return convertView;
    }

    @Override
    public Place getItem(int i){
        return places.get(i);
    }

    @Override
    public int getCount(){
        return places.size();
    }

    @NonNull
    @Override
    public Filter getFilter() {
        if(cs == null){
            cs = new CustomFilter();
        }
        return cs;
    }

    private int getIcon(Place place) {
        if(place instanceof Building)
            return R.drawable.ic_building;
        else if (place instanceof RoomModel)
            return R.drawable.ic_room;
        else if (place instanceof Floor)
            return R.drawable.ic_layers_pink;

        return R.drawable.ic_place_pink;
    }

    class CustomFilter extends Filter{

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();

            if(constraint != null && constraint.length()>0){
                constraint = constraint.toString().toUpperCase();

                ArrayList<Place> filters = new ArrayList();

                for(int i = 0; i<originalPlaces.size(); i++){
                    if(originalPlaces.get(i).getDisplayName().toUpperCase().contains(constraint)){
                        filters.add(originalPlaces.get(i));
                    }
                }

                results.count = filters.size();
                results.values = filters;
            }
            else{
                results.count = originalPlaces.size();
                results.values = originalPlaces;
            }

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            places = (ArrayList<Place>) results.values;
            notifyDataSetChanged();

        }

    }
}
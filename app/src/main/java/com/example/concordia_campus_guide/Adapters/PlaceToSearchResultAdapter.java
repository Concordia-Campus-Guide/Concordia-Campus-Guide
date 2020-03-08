package com.example.concordia_campus_guide.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.concordia_campus_guide.Models.Place;
import com.example.concordia_campus_guide.R;

import java.util.ArrayList;
import java.util.List;

public class PlaceToSearchResultAdapter extends ArrayAdapter<Place> implements Filterable {

    Context c;
    List<Place> places;
    CustomFilter cs;

    private static class ViewHolder {
        private TextView itemView;
    }

    public PlaceToSearchResultAdapter(Context context, int textViewResourceId, List<Place> items) {
        super(context, textViewResourceId, items);
        c = context;
        places = items;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;

        if (convertView == null) {
            convertView = LayoutInflater.from(this.getContext())
                    .inflate(R.layout.list_item_layout, parent, false);

            viewHolder = new ViewHolder();
            viewHolder.itemView = (TextView) convertView.findViewById(R.id.textView);

            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Place item = getItem(position);
        if (item!= null) {
            // My layout has only one TextView
            // do whatever you want with your string
            viewHolder.itemView.setText(item.getDisplayName());
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

    class CustomFilter extends Filter{

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();

            if(constraint != null && constraint.length()>0){
                constraint = constraint.toString().toUpperCase();

                ArrayList<Place> filters = new ArrayList();

                for(int i = 0; i<places.size(); i++){
                    if(places.get(i).getDisplayName().toUpperCase().contains(constraint)){
                        filters.add(places.get(i));
                    }
                }

                results.count = filters.size();
                results.values = filters;
            }
            else{
                results.count = places.size();
                results.values = places;
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
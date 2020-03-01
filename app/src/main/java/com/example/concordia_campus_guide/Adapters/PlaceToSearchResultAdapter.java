package com.example.concordia_campus_guide.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.concordia_campus_guide.Models.Place;
import com.example.concordia_campus_guide.R;

import java.util.List;

public class PlaceToSearchResultAdapter extends ArrayAdapter<Place> {

    private static class ViewHolder {
        private TextView itemView;
    }

    public PlaceToSearchResultAdapter(Context context, int textViewResourceId, List<Place> items) {
        super(context, textViewResourceId, items);
    }

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
            // do whatever you want with your string and long
            viewHolder.itemView.setText(item.getDisplayName());
        }

        return convertView;
    }

    @NonNull
    @Override
    public Filter getFilter() {
        return super.getFilter();
    }

//    class PlaceFilter extends Filter{
//        @Override
//        protected FilterResults performFiltering(CharSequence constraint){
//            FilterResults results = new FilterResults();
//
//            if(constraint!=null && constraint.lenth()>0){
//                constraint = constraint.toString().toUpperCase();
//            }
//
//            ArrayList<SingleRow> filters = new ArrayList<>;
//
//            for()
//        }
//
//        @Override
//        protected void publishResults(CharSequence charSequence, FilterResults filterResults){
//
//        }
//    }
}
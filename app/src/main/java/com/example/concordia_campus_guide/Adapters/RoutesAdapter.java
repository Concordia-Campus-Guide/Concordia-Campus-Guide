package com.example.concordia_campus_guide.Adapters;

import android.content.Context;
import android.widget.ArrayAdapter;

import com.example.concordia_campus_guide.Models.Place;
import com.example.concordia_campus_guide.Models.Route;

import java.util.List;

import androidx.annotation.NonNull;

public class RoutesAdapter extends ArrayAdapter<Route> {

    public RoutesAdapter(@NonNull Context context, int resource, int textViewResourceId, @NonNull List<Route> objects) {
        super(context, resource, textViewResourceId, objects);
    }
}

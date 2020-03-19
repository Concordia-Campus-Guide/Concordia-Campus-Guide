package com.example.concordia_campus_guide.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;
import android.os.Bundle;
import com.example.concordia_campus_guide.Fragments.LocationFragment.LocationFragment;
import com.example.concordia_campus_guide.Models.Place;
import com.example.concordia_campus_guide.R;

public class PathsActivity extends AppCompatActivity {

    PathsViewModel mViewModel;
    LocationFragment locationFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.paths_activity);

        mViewModel = ViewModelProviders.of(this).get(PathsViewModel.class);
        locationFragment = (LocationFragment) getSupportFragmentManager().findFragmentById(R.id.pathLocationFragment);

        Place from = mViewModel.getFrom();
        Place to = mViewModel.getTo();

        locationFragment.drawPaths(from, to);
    }
}

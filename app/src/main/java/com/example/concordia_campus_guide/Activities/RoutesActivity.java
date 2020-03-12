package com.example.concordia_campus_guide.Activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;

import com.example.concordia_campus_guide.R;

public class RoutesActivity extends AppCompatActivity {

    RoutesActivityViewModel mViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //set up activity
        super.onCreate(savedInstanceState);
        setContentView(R.layout.routes_activity);
        mViewModel = ViewModelProviders.of(this).get(RoutesActivityViewModel.class);

        //setup toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //get passed item
        long placeId = getIntent().getLongExtra("placeId", 0);
        String classExtendsPlaceType = getIntent().getStringExtra("classExtendsPlaceType");

        mViewModel.setToUsingPlaceId(placeId, classExtendsPlaceType);
    }
}
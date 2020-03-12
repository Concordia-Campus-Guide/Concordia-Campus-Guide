package com.example.concordia_campus_guide.Activities;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;

import com.example.concordia_campus_guide.R;

public class RoutesActivity extends AppCompatActivity {

    RoutesActivityViewModel mViewModel;

    TextView fromText;
    TextView toText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //set up activity
        super.onCreate(savedInstanceState);
        setContentView(R.layout.routes_activity);
        mViewModel = ViewModelProviders.of(this).get(RoutesActivityViewModel.class);

        //get view
        fromText = (TextView) findViewById(R.id.fromText);
        toText = (TextView) findViewById(R.id.toText);

        //setup toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //get passed item
        long placeId = getIntent().getLongExtra("placeId", 0);
        String classExtendsPlaceType = getIntent().getStringExtra("classExtendsPlaceType");
        mViewModel.setMyCurrentLocation((Location) getIntent().getParcelableExtra("myCurrentLocation"));
        mViewModel.setToUsingPlaceId(placeId, classExtendsPlaceType);

        this.fromText.setText(mViewModel.getFrom().getDisplayName());
        this.toText.setText(mViewModel.getTo().getDisplayName());

        setBackButtonOnClick();
    }

    public void onClickTo(View v){
        openSearchPage("to");
    }

    public void onClickFrom(View v){
        openSearchPage("from");
    }

    private void setBackButtonOnClick(){
        ImageButton backButton = (ImageButton)this.findViewById(R.id.routesPageBackButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void openSearchPage(String toOrFrom){
        Intent openSearch= new Intent(RoutesActivity.this,
                SearchActivity.class);

        openSearch.putExtra("toOrFrom", toOrFrom);

        startActivity(openSearch);
    }
}
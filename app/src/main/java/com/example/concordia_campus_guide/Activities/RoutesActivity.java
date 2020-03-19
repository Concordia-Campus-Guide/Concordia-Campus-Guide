package com.example.concordia_campus_guide.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;

import com.example.concordia_campus_guide.Global.SelectingToFromState;
import com.example.concordia_campus_guide.Models.Shuttle;
import com.example.concordia_campus_guide.Helper.ViewModelFactory;
import com.example.concordia_campus_guide.R;

import java.util.List;

public class RoutesActivity extends AppCompatActivity {

    RoutesActivityViewModel mViewModel;

    TextView fromText;
    TextView toText;
    TextView content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //set up activity
        super.onCreate(savedInstanceState);
        setContentView(R.layout.routes_activity);
        mViewModel = ViewModelProviders.of(this, new ViewModelFactory(this.getApplication())).get(RoutesActivityViewModel.class);
        mViewModel.setShuttles();
        //get view
        fromText = (TextView) findViewById(R.id.fromText);
        toText = (TextView) findViewById(R.id.toText);
        content = (TextView) findViewById(R.id.content);

        //setup toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //get passed item
        mViewModel.setFrom(SelectingToFromState.getFrom());
        mViewModel.setTo(SelectingToFromState.getTo());

        this.fromText.setText(mViewModel.getFrom().getDisplayName());
        this.toText.setText(mViewModel.getTo().getDisplayName());

        setBackButtonOnClick();
    }

    public void onClickTo(View v){
        SelectingToFromState.setSelectToToTrue();
        openSearchPage();
    }

    public void onClickFrom(View v){
        SelectingToFromState.setSelectFromToTrue();
        openSearchPage();
    }

    private void setBackButtonOnClick(){
        ImageButton backButton = (ImageButton)this.findViewById(R.id.routesPageBackButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { exitSelectToFrom(); }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)  {
        if (keyCode == KeyEvent.KEYCODE_BACK ) {
            exitSelectToFrom();

            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

    private void exitSelectToFrom(){
        Intent exitSelectToFrom= new Intent(RoutesActivity.this,
                MainActivity.class);

        SelectingToFromState.reset();

        startActivity(exitSelectToFrom);
    }

    private void openSearchPage(){
        Intent openSearch= new Intent(RoutesActivity.this,
                SearchActivity.class);
        startActivity(openSearch);
    }

    public void getShuttle(View view) {
        List<Shuttle> shuttles = mViewModel.getShuttles();
        String content = mViewModel.getShuttleDisplayText(shuttles);
        this.content.setText(content);
    }
}
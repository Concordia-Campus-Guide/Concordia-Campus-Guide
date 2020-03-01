package com.example.concordia_campus_guide.Activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.concordia_campus_guide.InfoCardFragment.InfoCardFragment;
import com.example.concordia_campus_guide.R;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

public class MainActivity extends AppCompatActivity {

    FragmentTransaction fragmentTransaction;
    FragmentManager fragmentManager;
    InfoCardFragment infoCardFragment;
    private BottomSheetBehavior swipeableInfoCard;
    private Button directions;
    private Button inddor_map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fragmentManager = getSupportFragmentManager();

        View infoCard = findViewById(R.id.info_card);
        swipeableInfoCard = BottomSheetBehavior.from(infoCard);

        directions = findViewById(R.id.directions);
        inddor_map = findViewById(R.id.indoor_map);
    }

    /**
     * Show the info card fragment in the view
     */
    public void showInfoCard(String buildingCode){
        if(infoCardFragment!=null){
            hideInfoCard();
        }
        Bundle bundle = new Bundle();
        bundle.putString("buildingCode", buildingCode );
        infoCardFragment = new InfoCardFragment();
        infoCardFragment.setArguments(bundle);
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.info_card_frame, infoCardFragment);
        fragmentTransaction.commit();

//        BottomSheetDialog swipeableInfoCard = new BottomSheetDialog(MainActivity.this);
//        swipeableInfoCard.setContentView(R.layout.info_card_fragment);
//        swipeableInfoCard.setCanceledOnTouchOutside(true);
//
//        Button directions = swipeableInfoCard.findViewById(R.id.directions);
//        Button indoor_map = swipeableInfoCard.findViewById(R.id.indoor_map);
//
//        swipeableInfoCard.show();

        swipeableInfoCard.setState(BottomSheetBehavior.STATE_COLLAPSED);

    }

    /**
     * Hides the info card fragment from the view.
     */
    public void hideInfoCard(){
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.remove(infoCardFragment);
        fragmentTransaction.commit();
    }

    /**
     * Defines the desired behavior on backpress
     */
    @Override
    public void onBackPressed(){
        Fragment fragment = fragmentManager.findFragmentById(R.id.info_card_frame);
        if(fragment!=null){
            hideInfoCard();
        }
        else{
            super.onBackPressed();
        }
    }
}
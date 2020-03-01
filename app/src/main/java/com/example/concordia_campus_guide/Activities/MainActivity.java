package com.example.concordia_campus_guide.Activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import com.example.concordia_campus_guide.InfoCardFragment.InfoCardFragment;
import com.example.concordia_campus_guide.R;

public class MainActivity extends AppCompatActivity {

    FragmentTransaction fragmentTransaction;
    FragmentManager fragmentManager;
    InfoCardFragment infoCardFragment;
    MainActivityViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mViewModel = ViewModelProviders.of(this).get(MainActivityViewModel.class);
        fragmentManager = getSupportFragmentManager();
        mViewModel.importBuildings(getApplicationContext());
    }

    public void showInfoCard(String buildingCode){
        if(infoCardFragment!=null){
            hideInfoCard();
        }

        infoCardFragment = new InfoCardFragment();
        infoCardFragment.setBuildingCode(buildingCode);

        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.info_card_frame, infoCardFragment);
        fragmentTransaction.commit();
    }

    public void hideInfoCard(){
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.remove(infoCardFragment);
        fragmentTransaction.commit();
    }

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
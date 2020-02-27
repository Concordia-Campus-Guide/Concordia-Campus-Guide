package com.example.concordia_campus_guide;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.concordia_campus_guide.InfoCardFragment.InfoCardFragment;

public class MainActivity extends AppCompatActivity {

    FragmentTransaction fragmentTransaction;

    InfoCardFragment infoCardFragment = new InfoCardFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
    }

    public void showInfoCard(){
        fragmentTransaction.add(R.id.info_card_frame, infoCardFragment);
        fragmentTransaction.commit();
    }

    public void hideInfoCard(){
        fragmentTransaction.remove(infoCardFragment);
        fragmentTransaction.commit();
    }
}
package com.example.concordia_campus_guide.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.concordia_campus_guide.InfoCardFragment.InfoCardFragment;

import com.example.concordia_campus_guide.R;

public class MainActivity extends AppCompatActivity {

    FragmentTransaction fragmentTransaction;
    FragmentManager fragmentManager;
    InfoCardFragment infoCardFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fragmentManager = getSupportFragmentManager();
        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        MainActivity.this.setTitle("ConUMaps");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();

        if(id == R.id.search){
            //TODO onClick of search_activity button
            Intent openSearch = new Intent(MainActivity.this,
                    SearchActivity.class);
            startActivity(openSearch);
            return false;
        }
        return true;
    }

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
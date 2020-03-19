package com.example.concordia_campus_guide.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import android.os.Bundle;

import com.example.concordia_campus_guide.Fragments.LocationFragment.LocationFragment;
import com.example.concordia_campus_guide.R;

public class PathsActivity extends AppCompatActivity {

    PathsViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.paths_activity);
//        if (savedInstanceState == null) {
//            getSupportFragmentManager().beginTransaction()
//                    .replace(R.id.container, PathsFragment.newInstance())
//                    .commitNow();
//        }
        LocationFragment f = (LocationFragment)getSupportFragmentManager().findFragmentById(R.id.pathsLocationFragment);

        f.drawPaths(mViewModel.from, mViewModel.to);
    }
}
